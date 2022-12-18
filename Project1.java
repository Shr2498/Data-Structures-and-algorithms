import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.Math;

class Project1 {
    static int i;
    static ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

    public void setS(int value) {
        i = value;
    }

    public int getS() {
        return i;
    }

    // if preferred partner is already taken
    Boolean preferenceDecisionFunction(ArrayList<ArrayList<Integer>> prefer, int woman, int man1,
            int man) {
        int n = prefer.get(0).size();
        for (int i = 0; i < n; i++) {

            if (prefer.get(woman + n - 1).get(i) == (man1 + 1)) {
                return true;
            }
            if (prefer.get(woman + n - 1).get(i) == (man + 1)) {
                return false;
            }
        }
        return false;
    }

    // function to find a stable matching by men or propose-dispose
    void PDFunction(ArrayList<ArrayList<Integer>> prefer, int n) {

        int[] WomenPartner = new int[n]; // array to store current men partners of women
        Boolean[] ManFree = new Boolean[n]; // array to store which men are free or not engaged
        int freeCount = n; // variable to keep count of free men

        // filling intial in WomanPartner array with -1
        Arrays.fill(WomenPartner, -1);
        // filling initial valus in ManFree array with false
        Arrays.fill(ManFree, false);

        while (freeCount > 0) {
            int m; // stores current index of men who is looking for women to marry
            int w; // stores current value of women who is to marry
            // looking for men who is free
            for (m = 0; m < n; m++) {
                if (ManFree[m] == false) {
                    break;
                }
            }

            for (int i = 0; i < n && ManFree[m] == false; i++) {
                w = prefer.get(m).get(i);
                // if preffered women is free then she would marry the man
                if (WomenPartner[w - 1] == -1) {
                    WomenPartner[w - 1] = m;
                    ManFree[m] = true;
                    freeCount--;
                } else {
                    // if preferred women is not free then preference list of woman would be checked
                    // in order to decide who is/will be married to that woman
                    int m1 = WomenPartner[w - 1];
                    if (preferenceDecisionFunction(prefer, w, m1, m) == false) {
                        WomenPartner[w - 1] = m;
                        ManFree[m] = true;
                        ManFree[m1] = false;
                    }
                }
            }
        }
        // Stable matching of propose-dispose algorithm to result arraylist
        for (int i = 0; i < n; i++) {
            Project1.result.add(new ArrayList<Integer>());
            Project1.result.get(i).add(WomenPartner[i] + 1);
            Project1.result.get(i).add(i + 1);
        }
    }

    // To find stable matching by women or propose-dispose-women
    void PDWFunction(ArrayList<ArrayList<Integer>> prefer, int n) {
        ArrayList<Integer> temp = new ArrayList<Integer>(); // arraylist object to store temp values

        // variable to store altered prefer arraylist
        // here the rows having preference values for women are exchanged with those of
        // men
        // in order to do reverse matching i.e from women to men
        ArrayList<ArrayList<Integer>> newPrefer = new ArrayList<ArrayList<Integer>>();

        // creating a shallow copy of prefer and storing into newPrefer
        for (int i = 0; i < 2 * n; i++) {
            newPrefer.add(new ArrayList<Integer>());
            for (int j = 0; j < n; j++) {
                newPrefer.get(i).add(prefer.get(i).get(j));
            }
        }

        for (int i = 0; i < n; i++) {
            temp.addAll(newPrefer.get(i));
            newPrefer.get(i).clear();
            newPrefer.get(i).addAll(newPrefer.get(i + n));
            newPrefer.get(i + n).clear();
            newPrefer.get(i + n).addAll(temp);
            temp.clear();
        }

        // array to store partners of men
        int[] mPartner = new int[n];
        // array to store which woman is free to marry
        Boolean[] wFree = new Boolean[n];
        int freeCount = n; // variable to keep count of which woman is free to marry

        // filling array with initial values.
        Arrays.fill(mPartner, -1);
        Arrays.fill(wFree, false);
        while (freeCount > 0) {
            int m;
            int w;
            // looking for women who are free
            for (m = 0; m < n; m++) {
                if (wFree[m] == false) {
                    break;
                }
            }

            for (int i = 0; i < n && wFree[m] == false; i++) {
                // if the preferred men is free then he will marry the woman
                w = newPrefer.get(m).get(i);
                if (mPartner[w - 1] == -1) {
                    mPartner[w - 1] = m;
                    wFree[m] = true;
                    freeCount--;
                } else {
                    // if the desired man is already married then preferencedecisionfunction would
                    // decide
                    // which woman will marry/stay married to that man.
                    int m1 = mPartner[w - 1];
                    if (preferenceDecisionFunction(newPrefer, w, m1, m) == false) {
                        mPartner[w - 1] = m;
                        wFree[m] = true;
                        wFree[m1] = false;
                    }
                }
            }
        }
        // adding stable matchings of propose-dispose-women algorithm to result
        // arraylist
        for (int i = 0; i < n; i++) {
            Project1.result.add(new ArrayList<Integer>());
            Project1.result.get(i + n).add(i + 1);
            Project1.result.get(i + n).add(mPartner[i] + 1);
        }
    }

    // function to calcualte difference between matchings
    public void calculateDifference(ArrayList<ArrayList<Integer>> prefer, int n) {
        int a, b, diff = 0;
        for (int i = 0; i < n; i++) {
            a = prefer.get(i).indexOf(Project1.result.get(i).get(1));
            b = prefer.get(i).indexOf(Project1.result.get(i + n).get(1));
            diff += Math.abs(a - b);
        }
        int w1 = 0, w2 = 0;
        for (int i = 0; i < n; i++) {
            w1 = Project1.result.get(i).get(1);
            a = prefer.get(i + n).indexOf(Project1.result.get(i).get(0));
            for (int j = 0; j < n; j++) {
                if (Project1.result.get(j + n).get(1) == w1) {
                    w2 = j + n;
                    break;
                }
            }
            b = prefer.get(i + n).indexOf(Project1.result.get(w2).get(0));
            diff += Math.abs(a - b);
        }
        System.out.println("\n" + diff);
    }

    // main code
    public static void main(String[] args) {
        Project1 s = new Project1();
        ArrayList<ArrayList<Integer>> prefer = new ArrayList<ArrayList<Integer>>();
        ArrayList<String> input = new ArrayList<String>(); // arraylist to store input from textfile
        try {
            // For taking input from "input.txt"
            FileInputStream fis = new FileInputStream("input.txt");
            Scanner sc = new Scanner(fis);

            int i = 0;

            while (sc.hasNext()) {
                if (i == 0) {
                    s.setS(Integer.parseInt(sc.nextLine()));
                } else {
                    input.add(sc.nextLine());
                }
                i++;
            }
            sc.close();
            // code to convert string input to arraylist
            String[] temp = new String[s.getS()];
            for (int p = 0; p < input.size(); p++) {
                temp = (input.get(p)).split(" ");
                prefer.add(new ArrayList<Integer>());
                for (int j = 0; j < s.getS(); j++) {
                    prefer.get(p).add(Integer.parseInt(temp[j]));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        int n = s.getS();
        // function calls
        s.PDFunction(prefer, n);
        s.PDWFunction(prefer, n);

        // Output display in console
        for (int i = 0; i < n * 2; i++) {
            System.out.println(Project1.result.get(i).get(0) + " " + Project1.result.get(i).get(1));

        }
        // calculating difference between stable matching and displaying the difference
        s.calculateDifference(prefer, s.getS());
    }
}

// Project 1 : Stable Matching
// Name : Bandaru Sai Venkata Shreyas
// CSUN ID : 203140710