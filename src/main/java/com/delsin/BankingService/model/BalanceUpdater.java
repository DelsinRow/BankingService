//package com.delsin.BankingService.model;
//
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.concurrent.ScheduledFuture;
//
//@Component
//public class BalanceUpdater {
//    private final TaskScheduler taskScheduler;
//    private ScheduledFuture<?> futureTask;
//
//    private BigDecimal balance = new BigDecimal("1000");
//    private final BigDecimal limit = new BigDecimal("1500");
//    private final BigDecimal percent = new BigDecimal("0.01");
//
//    public BalanceUpdater(TaskScheduler taskScheduler) {
//        this.taskScheduler = taskScheduler;
//    }
//
//    public void startScheduledTask() {
//        futureTask = taskScheduler.schedule(this::increaseBalance, new CronTrigger("0 * * * * *")); // Запуск задачи каждую минуту
//    }
//
//    private void increaseBalance() {
//        if (balance.compareTo(limit) >= 0) {
//            if (futureTask != null) {
//                futureTask.cancel(false); // Остановить задачу по достижению лимита
//            }
//            return;
//        }
//
//        BigDecimal increaseAmount = balance.multiply(percent);
//        balance = balance.add(increaseAmount);
//        System.out.println("Updated balance: " + balance);
//    }
//
//    public void stopScheduledTask() {
//        if (futureTask != null) {
//            futureTask.cancel(false); // Метод для явной остановки задачи
//        }
//    }
//}
