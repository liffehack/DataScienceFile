package com.company;
import java.io.*;
import java.util.*;

public class File implements Runnable{
    String path; // Путь к файлу
    Thread thread;  // Поток
    boolean ready = false;  // Чтение
    int sum = 0;

    // Инициализация
    public File(String path) {
        this.path = path;
        thread = new Thread(this);
        thread.start();
    }

    // Получение чтения потока
    public synchronized boolean IsReady(){
        return ready;
    }

    // Метод старта потока
    @Override
    public synchronized void run() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(FileInputStream fin = new FileInputStream(path)){

            int i=-1;
            while ((i=fin.read()) != -1){
                //System.out.println(i);
                baos.write(i);
            }
            String str = baos.toString().toLowerCase();
            System.out.println(str);

            // Алфавит
            //String abc = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
            String abc="abcdefghijklmnopqrstuvwxyz";

            Map<String, Integer> values = new HashMap<>();
            for (int j = 0; j < str.length(); j++) {
                String t = (str.substring(j,j+1));
                if (abc.contains(t)){
                    values.computeIfPresent(t, (k,v)->v+1);
                    values.putIfAbsent(t,1);
                }

            }

            for(Map.Entry<String,Integer> entry : values.entrySet()){
                Integer value = entry.getValue();
                sum = sum + value;
            }

            print(values);

            ready = true;
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    // Вывод данных
    public synchronized void print(Map<String, Integer> values) throws FileNotFoundException {
        String str="";
        List<Map.Entry<String, Integer>> list = new LinkedList<>(values.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        Map<String, Integer> result = new LinkedHashMap<>();
        for(Map.Entry<String, Integer> entry : list){
            result.put(entry.getKey(),entry.getValue());
        }

        for(Map.Entry<String,Integer> entry : result.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            double proc = (double)value/(double)sum*100.0;
            str+= key+ " используется "+proc+"% \n";
            System.out.println(key +" используется "+ proc + " % ");
        }
       // System.out.println(str);
        FileOutputStream fout = new FileOutputStream(("E:\\input.txt"));
        byte[] buffer= str.getBytes();
        try {
            fout.write(buffer,0,str.length()+100);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
