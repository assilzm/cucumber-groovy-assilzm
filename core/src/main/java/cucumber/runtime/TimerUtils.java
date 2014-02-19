package cucumber.runtime;

import gherkin.formatter.model.Result;
import gherkin.formatter.model.Step;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: MIC_WT
 * Date: 13-3-22
 * Time: 下午2:56
 * To change this template use File | Settings | File Templates.
 */
public class TimerUtils {
    private String _keyword;
    private String _name;

    public void start(Step step) {
        _keyword = step.getKeyword().trim();
        _name = step.getName().trim();
        System.out.println("\r\n[" + _keyword + ": " + _name + "] start.");
    }

    public void end(Result _result) {
        long _cost = _result.getDuration() == null ? 0 : _result.getDuration();
        //尽早输出错误信息
        if (_result.getStatus().equals(Result.FAILED))
            System.out.println(_result.getErrorMessage());
        System.out.println("\r\n[" + _keyword + ": " + _name + "] "
                + _result.getStatus() + " in " + formatDuration(_cost));
    }

    public String formatDuration(long cost) {
        return String.format("%d min %d sec %d ms",
                TimeUnit.NANOSECONDS.toMinutes(cost),
                TimeUnit.NANOSECONDS.toSeconds(cost) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(cost)),
                TimeUnit.NANOSECONDS.toMillis(cost) - TimeUnit.SECONDS.toMillis(TimeUnit.NANOSECONDS.toSeconds(cost))
        );
    }

    public static void main(String[] arg) throws Exception {
        TimerUtils timer = new TimerUtils();
        long start = System.nanoTime();
        Thread.sleep(1000L);
        long end = System.nanoTime();
        System.out.println(end - start);
        System.out.println(timer.formatDuration(end - start));
    }

}

