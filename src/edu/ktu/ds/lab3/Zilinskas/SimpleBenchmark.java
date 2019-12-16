package edu.ktu.ds.lab3.Zilinskas;

import edu.ktu.ds.lab3.gui.ValidationException;
import edu.ktu.ds.lab3.utils.HashMapOa;
import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.in;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @author eimutis
 */
public class SimpleBenchmark {

    public static final String FINISH_COMMAND = "                               ";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("edu.ktu.ds.lab3.Zilinskas.messages");

    private final Timekeeper timekeeper;
    private final LinkedHashMap<String, String> mapp;
    private final String[] BENCHMARK_NAMES = {"remKTU", "remJAVA", "remOAKTU", "remJava"};
    private final int[] COUNTS = {10000, 20000, 40000, 80000};
    private final Queue<String> chainsSizes = new LinkedList<>();

    /**
     * For console benchmark
     */
    public SimpleBenchmark() throws IOException {
        this.mapp = LoadFile();
        timekeeper = new Timekeeper(COUNTS);
    }

    /**
     * For Gui benchmark
     *
     * @param resultsLogger
     * @param semaphore
     */
    public SimpleBenchmark(BlockingQueue<String> resultsLogger, Semaphore semaphore) throws IOException {
        this.mapp = LoadFile();
        semaphore.release();
        timekeeper = new Timekeeper(COUNTS, resultsLogger, semaphore);
    }

    public static void main(String[] args) throws IOException {
        executeTest();
    }

    public static void executeTest() throws IOException {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        Ks.out("Greitaveikos tyrimas:\n");
        new SimpleBenchmark().startBenchmark();
    }

    public void startBenchmark() {
        try {
            benchmark();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public LinkedHashMap<String, String> LoadFile() throws IOException{
        File file = new File("data/zodynas.txt"); 
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            String st; 
            while ((st = br.readLine()) != null){
                map.put(st,st);
            }
        }catch(FileNotFoundException e){
            e.printStackTrace(System.out); 
            return null;
        }
        return map;
    }
    
    public String[] getRandom(int count){
        Random generator = new Random();
        String[] map2 = new String[count];
        Object[] values = mapp.values().toArray();
        if(values.length != 0){
            for(int i = 0; i < count; i++){
                String randomValue = (String)values[generator.nextInt(values.length)];
                map2[i] = randomValue;
            }
        }
        return map2;
    }

    public void benchmark() throws InterruptedException {
        //3 8
        try {
            for (int k : COUNTS) {
                edu.ktu.ds.lab3.utils.HashMap<String, String> ktu = new edu.ktu.ds.lab3.utils.HashMap<>();
                java.util.HashMap<String,String> java = new java.util.HashMap<>();
                HashMapOa<String,String> ktuOa = new HashMapOa<>();

                //Java
                String[] random = getRandom(k);
                //KTU
                String[] random2 = getRandom(k);
                timekeeper.startAfterPause();
                timekeeper.start();
                Arrays.stream(random).forEach(java::remove);
                timekeeper.finish(BENCHMARK_NAMES[0]);
                Arrays.stream(random2).forEach(ktu::remove);
                timekeeper.finish(BENCHMARK_NAMES[1]);
                Arrays.stream(random2).forEach(java::remove);
                timekeeper.finish(BENCHMARK_NAMES[2]);
                Arrays.stream(random2).forEach(ktuOa::remove);
                timekeeper.finish(BENCHMARK_NAMES[2]);
                timekeeper.seriesFinish();
            }

            StringBuilder sb = new StringBuilder();
            chainsSizes.forEach(p -> sb.append(p).append(System.lineSeparator()));
            timekeeper.logResult(sb.toString());
            timekeeper.logResult(FINISH_COMMAND);
        } catch (ValidationException e) {
            timekeeper.logResult(e.getMessage());
        }
    }
}
