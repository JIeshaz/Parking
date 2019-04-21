package com.company;

import com.company.entities.Car;
import com.company.entities.Parking;
import com.company.entities.Ticket;
import com.company.threads.Enter;
import com.company.threads.Quit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        System.out.print("����� ����������!\n" +
                "������� ���-�� ����������� ����: ");
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        int space;
        while(true)
            try {
                space = Integer.parseInt(r.readLine());
                if (space > 0)
                    break;
                else System.out.println("������� ����� ��������������� ����� ������ ����!");
            }
            catch (Exception e) {
                System.out.println("������� ����� ��������������� ����� ������ ����!");
            }
        Parking.setTotalspace(space);
            for (int i=0; i<space; i++)
                Parking.getTickets().add(new Ticket(i));

        String text = "";

        int counter = 1;
        int cparse;
        int[] arr;

        while (true) {
            System.out.println("������� ������� (help ��� ������ ������):");
            try {
                text = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (text.equals("exit") || text.equals("e"))
                break;
            if (text.equals("help") || text.equals("h")) {
                System.out.println("�������:\n" +
                        "p:N - (park) ����� ������������ ������, � ��������� ������ ��������, ��� N - ���������� ����� �� �����\n" +
                        "u:N - (unpark) ����� ������� � ��������. N - ����� ������������ ������\n" +
                        "u:[1..n] - (unpark) ����� ������� � �������� ���������� �������, ��� � ���������� �������, ����� ������� ���������� ������ ����������� �������\n" +
                        "l - (list) ������ �����, ����������� �� ��������. ��� ������ ������ ��������� �� ���������� ����� � ����� ������\n" +
                        "c - (count) ���������� ���������� ���� �� ��������\n" +
                        "e - (exit) ����� �� ����������\n" +
                        "t:N - (time) ���������� ����� ��� ������ � ������ ����� ������ (N - �� 1 �� 5 ���)\n" +
                        "h - (help) ����� ������ ������");
            } else if (text.matches("p:\\d+")) {
                //� ����� ������ ����� ��� ���
                cparse = Integer.parseInt(text.split("p:")[1]);
                if (Parking.getTickets().size() >= cparse) {
                    Enter enterthread = new Enter();
                    enterthread.setCars(cparse);
                    enterthread.start();
                } else
                    System.out.println("�� �������� ��� ������� ��������� ����!");
            } else if (text.matches("u:\\d+")) {
                cparse = Integer.parseInt(text.split("u:")[1]);
                if (Parking.getTotalspace() >= cparse) {
                    counter = -1;
                    for (Map.Entry<Ticket, Car> each : Parking.getMapping().entrySet()) {
                        Quit thread = new Quit();
                        if (each.getKey().getId() == cparse) {
                            thread.setTicket(each.getKey());
                            thread.start();
                            counter = 1;
                        }
                    }
                    if (counter == -1) {
                        System.out.println("�� ������������ ������ � ������� �" + cparse);
                    }
                }
                else System.out.println("��� ������ ������");
            } else if (text.matches("u:\\[(\\d+,)+\\d+]")) {
                arr = Arrays.stream(text.split("u:")[1].substring(1, text.split("u:")[1].length() - 1).split(","))
                        .map(String::trim).mapToInt(Integer::parseInt).toArray();
                for (int each1 : arr) {
                    if (Parking.getTotalspace() >= each1) {
                        counter = -1;
                        for (Map.Entry<Ticket, Car> each : Parking.getMapping().entrySet()) {
                            Quit thread = new Quit();
                            if (each.getKey().getId() == each1) {
                                thread.setTicket(each.getKey());
                                thread.start();
                                counter = 1;
                            }
                        }
                        if (counter == -1) {
                            System.out.println("�� ������������ ������ � ������� �" + each1);
                        }
                    }
                }
            } else if (text.equals("l") || text.equals("list")) {
                counter = 1;
                System.out.println("������ �� �������� " + Parking.getMapping().size() + " �����");
                if (Parking.getMapping().size()>0)
                        for (Map.Entry<Ticket, Car> each: Parking.getMapping().entrySet()) {
                            System.out.println("#"+counter + " | � ������: " + each.getKey().getId());
                            counter++;
                        }
            } else if (text.equals("c") || text.equals("count")) {
                System.out.println("������ �� �������� " + (Parking.getTotalspace()-Parking.getMapping().size()) + " ��������� ����");
            } else if (text.matches("t:\\d+")) {
                cparse = Integer.parseInt(text.split("t:")[1]);
                if (cparse >= 1 && cparse <=5) {
                    Enter.setSleeptime(cparse);
                    Quit.setSleeptime(cparse);
                    System.out.println("����� ������/������ ����������� �� "+cparse+" ������");
                } else {
                    System.out.println("������. �������� ����� ���� ������ �� 1 �� 5! ������� � ����.");
                }
            } else
                System.out.println("������������ �������. ���������� ��� ���.");
        }
        System.out.print("������ ���������!");
        try {
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
