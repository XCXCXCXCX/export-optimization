package com.xcxcxcxcx.export;

import java.util.List;

/**
 * @author XCXCXCXCX
 * @date 2020/8/24 11:24 上午
 */
public class Result {

    private boolean success = true;

    private long cost;

    private Throwable exception;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        if (exception != null) {
            success = false;
        }
        this.exception = exception;
    }

    public static void showResults(List<Result> results) {
        System.out.println("本次执行" + results.size() + "个导出任务");
        int count = 0;
        for (Result result : results) {
            if (result.isSuccess()) {
                count++;
            }
        }
        System.out.println("成功数: " + count);
        System.out.println("失败数: " + (results.size() - count));
        StringBuilder sb = new StringBuilder("失败原因: \n");
        for (int i = 0; i < results.size(); i++) {
            Throwable e = results.get(i).getException();
            sb.append("[").append(i).append("] ").append(e != null ? e.getMessage() : null).append("\n");
        }
        sb.append("耗时: \n");
        long sum = 0;
        for (int i = 0; i < results.size(); i++) {
            sum += results.get(i).getCost();
            sb.append("[").append(i).append("] cost ").append(results.get(i).getCost()).append(" ms\n");
        }
        sb.append("平均耗时: ").append((double) sum / results.size()).append(" ms\n");
        System.out.println(sb.toString());
    }
}
