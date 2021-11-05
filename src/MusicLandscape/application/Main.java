package MusicLandscape.application;

import MusicLandscape.container.MyTrackContainer;
import MusicLandscape.entities.Track;
import MusicLandscape.util.MyFormatter;
import MusicLandscape.util.MyMatcher;
import MusicLandscape.util.comparators.*;
import MusicLandscape.util.formatters.CSVTrackFormatter;
import MusicLandscape.util.formatters.LongTrackFormatter;
import MusicLandscape.util.formatters.ShortTrackFormatter;
import MusicLandscape.util.formatters.XMLTrackFormatter;
import MusicLandscape.util.io.MyTrackCSVReader;
import MusicLandscape.util.io.MyTrackXMLReader;
import MusicLandscape.util.io.MyWriter;
import MusicLandscape.util.io.MyXMLWriter;
import MusicLandscape.util.matcher.*;

import java.io.*;
import java.util.*;

public class Main {

    private MyTrackContainer db = new MyTrackContainer();
    private List<Comparator<Track>> comparators = new LinkedList<Comparator<Track>>();
    private List<MyFormatter<Track>> formatters = new LinkedList<MyFormatter<Track>>();
    private List<MyMatcher<Track>> matchers = new LinkedList<MyMatcher<Track>>();

    private Comparator<Track> theComp;
    private boolean asc = true;

    private MyFormatter<Track> theFormat;
    private MyMatcher<Track> placeboMatcher = new TitleMatcher("");
    private Main.Menu menu = new Menu();

    {
        comparators.add(theComp = new TitleComparator());
        comparators.add(new DurationComparator());
        comparators.add(new WriterComparator());
        comparators.add(new PerformerComparator());
        comparators.add(new YearComparator());

        matchers.add(placeboMatcher);
        matchers.add(new WriterMatcher());
        matchers.add(new PerformerMatcher());
        matchers.add(new ArtistMatcher());
        matchers.add(new DurationMatcher());
        matchers.add(new YearMatcher());

        formatters.add(theFormat = new LongTrackFormatter());
        formatters.add(new ShortTrackFormatter());
        formatters.add(new CSVTrackFormatter());
        formatters.add(new XMLTrackFormatter());
    }

    private static final String WELCOME_TEXT = "Welcome to the FinalTrackDataBase";
    private static final String GOOD_BYE_TEXT = "Thank you for using FinalTrackDataBase";

    public void go() {

        System.out.println(WELCOME_TEXT);
        Scanner sc = new Scanner(System.in);
        menu.execute(0);
        int menu_length = this.menu.menu.length;
        while (true) {
            // display(db);

            // get choice
            System.out.print(": ");
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (0 > input || input >= menu_length)
                    throw new IndexOutOfBoundsException();

                if (menu.execute(input))
                    continue;

                System.out.print("exit? (1=yes)");
                if (Integer.parseInt(sc.nextLine()) == 1)
                    break;
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("\t" + " You need to enter a number between 0 (inclusive) and " + menu_length + " (exclusive).");
            }
        }

        System.out.println(GOOD_BYE_TEXT);
        sc.close();
    }

    public void display(MyTrackContainer db) {

        if (db.size() == 0) {
            System.out.print( "\t" + "no records stored.\n");
            return;
        }
        if (db.selection().length == 0) {
            System.out.print("\t" + "selection empty.\n");
            return;
        }

        System.out.println('\n' + theFormat.header());
        System.out.println( theFormat.topSeparator());
        for (Track tt : db.selection())
            System.out.println(theFormat.format(tt));
        System.out.println();

        System.out.printf("\t" + "%d out of %d records selected\n", db.selection().length,
                db.size());
    }

    public static void main(String[] args) {
        new Main().go();
    }


    private class Menu {

        public void show_menu() {
            display();
        }

        public void display_selection() {
            System.out.printf("\t" + "displaying selection:\n");

            Main.this.display(db);
        }

        public void menu_edit() {
            if (Main.this.db.selection().length == 0) {
                System.out.println("\t" + "Please Select tracks, Selection is empty.");
                return;
            }
            Scanner sc = new Scanner(System.in);
            int i = 0;
            System.out.println("\t" + theFormat.header());
            System.out.println("\t" + theFormat.topSeparator());
            for (Track tt : db.selection())
                System.out.println("\t" + (i++) + ": " + theFormat.format(tt));
            System.out.println();
            System.out.print("\t" + "select track to edit: ");
            int input;
            try {
                input = Integer.parseInt(sc.nextLine());
                if (input < Main.this.db.selection().length) {
                    System.out.println("\t" + Main.this.db.selection()[input] + " selected.");
                } else {
                    System.out.println("\t Invalid number. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\t" + "You need to enter a number between 0 and " + Main.this.db.selection().length + ".");
                return;
            }
            Main.this.db.selection()[input].scan();
        }

        public void menu_filter() {
            if (Main.this.db.selection().length == 0) {
                System.out.println("\t Selection is empty. Please select first by resetting.");
                return;
            }
            Scanner sc = new Scanner(System.in);
            for (int i = 0; i < Main.this.matchers.size(); i++) {
                System.out.println("\t" + i + ": " + Main.this.matchers.get(i).toString());
            }

            System.out.print("\t" + "select filtering: ");
            try {
                int input_choice = Integer.parseInt(sc.nextLine());
                System.out.print("\t" + "enter pattern: ");
                String input_pattern = sc.nextLine();

                if (input_choice < Main.this.matchers.size()) {
                    Main.this.placeboMatcher = Main.this.matchers.get(input_choice);
                    Main.this.placeboMatcher.setPattern(input_pattern);
                    System.out.println("\t" + Main.this.placeboMatcher.toString()
                            + " filter applied (" + Main.this.db.filter(Main.this.placeboMatcher) + " record/s filtered).");
                } else {
                    System.out.println("\t" + "Invalid choice of filtering.");
                    System.out.println("\t" + "You are back in Main Menu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\t" + "You need to enter a number between 0 and " + Main.this.matchers.size() + ".");
            } catch (Exception e) {
                System.out.println("\t" + e.getMessage());
            }

        }

        public void menu_reset() {
            Main.this.db.reset();
            for (MyMatcher<Track> matcher : Main.this.matchers) {
                if (matcher instanceof TitleMatcher || matcher instanceof WriterMatcher
                        || matcher instanceof PerformerMatcher || matcher instanceof ArtistMatcher) {
                    matcher.setPattern("");
                } else if (matcher instanceof DurationMatcher) {
                    matcher.setPattern("0 " + Integer.MAX_VALUE);
                } else if (matcher instanceof YearMatcher) {
                    matcher.setPattern("1900 2999");
                }
            }
            System.out.println("\t" + "Selection is reset. All tracks in the database are selected.");
        }

        public void menu_remove_selection() {
            System.out.println("The number of tracks removed from the database is/are " + Main.this.db.remove() + ".");
        }

        public void menu_add() {
            Track track = new Track();
            if (track.scan() && Main.this.db.add(track)) {
                System.out.println("Track has been added to the database!");
            } else {
                System.out.println("Adding new Track was canceled.");
            }
        }

        public void menu_save_selection_csv() {
            Scanner sc = new Scanner(System.in);
            System.out.print("\t" + "Enter target file name: ");
            try {
                String file_name = sc.nextLine();
                if (!"".equals(file_name)) {
                    MyWriter<Track> my_writer = new MyWriter<>(new FileWriter(file_name), new CSVTrackFormatter());
                    int counter = 0;
                    for (Track track : Main.this.db.selection()) {
                        if (my_writer.put(track)) {
                            counter++;
                        }
                    }
                    System.out.println("\t" + counter + " track/s written.");
                    my_writer.close();
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("\t" + e.getMessage());
            } catch (Exception e) {
                System.out.println("\t" + e.getMessage());
            }
        }

        public void menu_save_selection_xml() {
            Scanner sc = new Scanner(System.in);
            System.out.print("\t" + "Enter target file name: ");
            try {
                String file_name = sc.nextLine();
                if (!"".equals(file_name)) {
                    MyXMLWriter xml_writer = new MyXMLWriter(new FileWriter(file_name), Main.this.theFormat);
                    int nrTracks = xml_writer.saveToXML(Main.this.db.selection());
                    xml_writer.createDTDFile();

                    System.out.println("\t" + nrTracks + " track/s written.");
                    xml_writer.close();
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("\t" + e.getMessage());
            } catch (Exception e) {
                System.out.println("\t" + e.getMessage());
            }
        }

    public void menu_load_from_csv() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\t" + "Enter target file name: ");
        String file_name = "";
        try {
            file_name = sc.nextLine();
            if (!"".equals(file_name)) {
                MyTrackCSVReader csv_reader = new MyTrackCSVReader(new BufferedReader(new FileReader(file_name)));
                Track track;
                int counter = 0;
                while ((track = csv_reader.get()) != null) {
                    if (Main.this.db.add(track)) {
                        System.out.println(String.format("\t" + "%s, %s, %s, %d, %d",
                                track.getTitle(), track.getWriter(), track.getPerformer(),
                                track.getDuration(), track.getYear()));
                        counter++;
                    }
                }
                System.out.println("\t" + counter + " track/s imported.");
            } else {
                System.out.println("\t You need to enter a filename.");
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            //System.err.println("\t" + e.getMessage());
            System.out.println(String.format("\t" + "Error: cannot open file (%s).", file_name));
        } catch (Exception e) {
            System.out.println("\t" + e.getMessage());
        }
    }

    public void menu_load_from_xml() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\t" + "Enter target file name: ");
        String file_name = "";
        try {
            file_name = sc.nextLine();
            if (!"".equals(file_name)) {
                MyTrackXMLReader xml_reader = new MyTrackXMLReader(new BufferedReader(new FileReader(file_name)), file_name);
                xml_reader.createXMLTrackArray();
                Track track;
                int counter = 0;
                while ((track = xml_reader.get()) != null) {
                    if (Main.this.db.add(track)) {
                        System.out.println(String.format("\t" + "%s, %s, %s, %d, %d",
                                track.getTitle(), track.getWriter(), track.getPerformer(),
                                track.getDuration(), track.getYear()));
                        counter++;
                    }
                }
                System.out.println("\t" + counter + " track/s imported.");
            } else {
                System.out.println("\t You need to enter a filename.");
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            //System.err.println("\t" + e.getMessage());
            System.out.println(String.format("\t" + "Error: cannot open file (%s).", file_name));
        }catch (Exception e) {
            System.out.println("\t" + e.getMessage());
        }
    }

    public void menu_reverse_sorting_order() {
        if (Main.this.asc == true)
            Main.this.asc = false;
        else
            Main.this.asc = true;

        Main.this.db.sort(Main.this.theComp, Main.this.asc);
        System.out.println(String.format("\t" + "selection sorted %s (%s)",
                Main.this.theComp.toString(), (Main.this.asc ? "ascending" : "descending")));
    }

    public void menu_select_sorting() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < Main.this.comparators.size(); i++) {
            System.out.println("\t" + i + ": " + Main.this.comparators.get(i).toString());
        }

        System.out.print("\t" + "select sorting: ");
        int input;
        try {
            input = Integer.parseInt(sc.nextLine());

            if (input < Main.this.comparators.size()) {
                Main.this.theComp = Main.this.comparators.get(input);
                Main.this.db.sort(Main.this.theComp, Main.this.asc);
                System.out.println("\t" + "selection sorted " + Main.this.theComp.toString()
                        + (Main.this.asc ? " (ascending)." : " (descending)."));
            } else {
                System.out.println("\t" + "Invalid choice of sorting.");
                System.out.println("\t" + "You are back in Main Menu.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\t" + "You need to enter a number between 0 and " + Main.this.comparators.size() + ".");
        }
    }

    public void menu_select_formatting() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < Main.this.formatters.size(); i++) {
            System.out.println("\t" + i + ": " + Main.this.formatters.get(i).toString());
        }
        System.out.print("\t" + "select formatting: ");
        int input;
        try {
            input = Integer.parseInt(sc.nextLine());

            if (input < Main.this.formatters.size()) {
                Main.this.theFormat = Main.this.formatters.get(input);
                System.out.println("\t" + Main.this.theFormat + " selected.");
            } else {
                System.out.println("\t" + "Invalid choice of formatter.");
                System.out.println("\t" + "You are back in Main Menu.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\t" + "You need to enter a number between 0 and " + Main.this.formatters.size() + ".");
        }
    }

    private Main.MenuItem[] menu = {

            new Main.MenuItem("show menu") {
                @Override
                void execute() {
                    show_menu();
                }
                // end of MenuItem id=0
            },
            new Main.MenuItem("display selection") {
                @Override
                void execute() {
                    display_selection();
                }
                // end of MenuItem id=1
            },
            new Main.MenuItem("edit") {
                @Override
                void execute() {
                    menu_edit();
                }
                // end of MenuItem id=2
            },
            new Main.MenuItem("filter") {
                @Override
                void execute() {
                    menu_filter();
                }
                // end of MenuItem id=3
            },
            new Main.MenuItem("reset") {
                @Override
                void execute() {
                    menu_reset();
                }
                // end of MenuItem id=4
            },
            new Main.MenuItem("remove selection") {
                @Override
                void execute() {
                    menu_remove_selection();
                }
                // end of MenuItem id=5
            },
            new Main.MenuItem("add") {
                @Override
                void execute() {
                    menu_add();
                }
                // end of MenuItem id=6
            },
            new Main.MenuItem("save selection as .csv file") {
                @Override
                void execute() {
                    menu_save_selection_csv();
                }
                // end of MenuItem id=7
            },
            new Main.MenuItem("save selection as .xml file") {
                @Override
                void execute() {
                    menu_save_selection_xml();
                }
                // end of MenuItem id=8
            },
            new Main.MenuItem("load from .csv file") {
                @Override
                void execute() {
                    menu_load_from_csv();
                }
                // end of MenuItem id=9
            },
            new Main.MenuItem("load from .xml file") {
                @Override
                void execute() {
                    menu_load_from_xml();
                }
                // end of MenuItem id=10
            },
            new Main.MenuItem("reverse sorting order") {
                @Override
                void execute() {
                    menu_reverse_sorting_order();
                }
                // end of MenuItem id=11
            },
            new Main.MenuItem("select sorting") {
                @Override
                void execute() {
                    menu_select_sorting();
                }
                // end of MenuItem id=12
            },
            new Main.MenuItem("select formatting") {
                @Override
                void execute() {
                    menu_select_formatting();
                }
                // end of MenuItem id=13
            },

    };// end of array Main.MenuItem[] menu;

    void display() {
        for (Main.MenuItem m : this.menu) {
            System.out.println(m);
        }
    }

    public boolean execute(int input) {
        for (Main.MenuItem m : this.menu) {
            if (m != null && m.id == input) {
                m.execute();
                return true;
            }
        }
        return false;

    }

}

private abstract static class MenuItem {


    private static int nextID = 0;
    private final int id = nextID++;
    private String text;

    MenuItem(String s) {
        this.text = s;
    }

    abstract void execute();

    public String toString() {

        return this.id + "\t" + this.text;
    }
}
}

