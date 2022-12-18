import java.io.*;
import java.util.*;

public class Project2 {
    public static void main(String[] args)
    {
       List<int[]> boxes = readInputFile("input.txt");
       int height = highest(boxes);
       System.out.println(height);
   }

    public static int highest(List<int[]> boxes) 
    {
        int n = boxes.size();
        Collections.sort(boxes, (a, b) -> 
        {
            if (a[0] == b[0]) 
            {
                return b[1] - a[1];
            }
            return b[0] - a[0];
        });
        int[] d = new int[n + 1];
        d[0] = 0;
        d[1] = boxes.get(0)[2];

        for (int i = 2; i <= n; i++) 
        {
            int[] box = boxes.get(i - 1);
           
            int optVal = box[2];
            for (int j = i - 1; j >= 1; j--) 
            {
                int[] otherBox = boxes.get(j - 1);
        
                if (box[0] < otherBox[0] && box[1] < otherBox[1]) 
                {
                    optVal = Math.max(optVal, d[j] + box[2]);
                }
            }
            d[i] = optVal;
        }

        int maxH = 0;
        for (int i = 1; i <= n; i++) {
            maxH = Math.max(maxH, d[i]);
        }
        return maxH;
    }
    public static List<int[]> readInputFile(String filename)
     {
        List<int[]> boxes = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(new File(filename))))
         {
            int n = Integer.parseInt(br.readLine());
            for (int i = 0; i < n; i++) 
            {
                String[] box = br.readLine().split(" ");
                boxes.add(new int[]{Integer.parseInt(box[0]), Integer.parseInt(box[1]), Integer.parseInt(box[2])});
            }
         }
          catch (IOException e) 
          {
            System.out.println("File not read" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return boxes;
    }

   
}



// Project 2 : Stacked boxes
// Name : Bandaru Sai Venkata Shreyas
// CSUN ID : 203140710


