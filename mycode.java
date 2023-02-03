import java.io.*;
import java.util.*;

public class mycode {
    public static void main(String[] args) {
        int pointsSpend = Integer.parseInt(args[0]);

        System.out.println(calculate(pointsSpend));
    }

    private static HashMap<String, Integer> calculate(int pointsSpend) {
        List<List<String>> records = new ArrayList<>();
        HashMap<String, Integer> payers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("transactions.csv"))) {
            String line;

            // First line of csv should be ""payer","points","timestamp""
            String nameTag = br.readLine();
            String[] tempValue = nameTag.split(",");
            // Get the first element of first line, it should be "payer"
            String tmp = new String(tempValue[0].substring(1, tempValue[0].length() - 1));
            // Making sure wheter the first line is tag or not, if it is tag, do nothing,
            // else add it to records
            if (!tmp.equals("payer")) {
                records.add(Arrays.asList(tempValue));
            }

            // Add all other lines to records
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        records.sort((d1, d2) -> d1.get(2).compareTo(d2.get(2)));

        for (List<String> r : records) {
            String name = r.get(0);
            int point = Integer.parseInt(r.get(1));
            if (pointsSpend >= point) {
                pointsSpend -= point;
                payers.put(name, payers.getOrDefault(name, 0));
            } else {
                point -= pointsSpend;
                pointsSpend = 0;
                payers.put(name, payers.getOrDefault(name, 0) + point);
            }
        }

        return payers;
    }

}