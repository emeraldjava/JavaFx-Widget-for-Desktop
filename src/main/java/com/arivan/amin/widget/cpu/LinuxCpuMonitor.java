package com.arivan.amin.widget.cpu;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LinuxCpuMonitor implements CpuMonitor
{
    private static final Pattern EXTRA_SPACE = Pattern.compile(" {2,}");
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final List<String> cpuUsageCommand = List.of("cat", "/proc/stat");
    private List<Integer> prevData;
    
    private LinuxCpuMonitor ()
    {
        super();
        prevData = getUsageData();
    }
    
    @NotNull
    public static LinuxCpuMonitor newInstance ()
    {
        return new LinuxCpuMonitor();
    }
    
    @Override
    public double getCpuUsage ()
    {
        List<Integer> numbersList = getUsageData();
        List<Integer> deltaList = IntStream.range(0, numbersList.size())
                .mapToObj(i -> numbersList.get(i) - prevData.get(i))
                .collect(Collectors.toCollection(() -> new ArrayList<>(20)));
        double deltaSum = deltaList.stream().mapToInt(Integer::intValue).sum();
        double usedCpu = deltaSum - deltaList.get(3);
        double percent = (PERCENT * usedCpu) / deltaSum;
        percent /= 100;
        prevData = numbersList;
        return percent;
    }
    
    private List<Integer> getUsageData ()
    {
        try
        {
            String output = getCommandOutput(cpuUsageCommand);
            output = output.substring(0, output.indexOf(System.lineSeparator()));
            output = EXTRA_SPACE.matcher(output).replaceAll(" ");
            String cpu = "cpu ";
            output = output.substring(output.indexOf(cpu) + cpu.length());
            List<String> outputList = List.of(output.split(" "));
            return outputList.stream().map(Integer::parseInt).collect(Collectors.toList());
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return Collections.emptyList();
        }
    }
}
