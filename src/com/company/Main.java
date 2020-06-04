package com.company;

    public class Main {

        public static void main(String[] args) {
            // Загрузим файл
            File file = new File("E:\\output.txt");

            while (file.IsReady())
            {
                try {
                    Thread.sleep(1);
                    System.out.println("Work");
                }
                catch (InterruptedException ex){
                    System.out.println("Error");
                }
            }
        }
}
