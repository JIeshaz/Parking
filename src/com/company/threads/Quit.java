package com.company.threads;

import com.company.entities.Parking;
import com.company.entities.Ticket;

public class Quit extends Thread {
    private static int sleeptime = 5000;
    private Ticket ticket;

    public static void setSleeptime(int sleeptime) {
        Quit.sleeptime = sleeptime*1000;
    }

    @Override
    public synchronized void run() {
        System.out.println("������ "+ Parking.getMapping().get(ticket).getLicenseplate() +" c ������� �" + ticket.getId() + " ���������� ������");
        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("������ "+ Parking.getMapping().get(ticket).getLicenseplate() +" c ������� �" + ticket.getId() + " ������");
        Parking.deleteMapping(ticket);
    }

    public synchronized void setTicket(Ticket ticket){
        this.ticket = ticket;
    }

}
