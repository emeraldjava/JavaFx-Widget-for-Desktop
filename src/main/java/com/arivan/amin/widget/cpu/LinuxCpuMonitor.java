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
        return computeUsedPercent();
    }
    
    private double computeUsedPercent ()
    {
        List<Integer> deltaList = computeDifferenceBetweenReads();
        double deltaSum = sumDeltaValues(deltaList);
        double usedCpu = deltaSum - deltaList.get(3);
        return ((PERCENT * usedCpu) / deltaSum) / 100;
    }
    
    private double sumDeltaValues (Collection<Integer> deltaList)
    {
        return deltaList.stream().mapToInt(Integer::intValue).sum();
    }
    
    private List<Integer> computeDifferenceBetweenReads ()
    {
        List<Integer> numbersList = getUsageData();
        List<Integer> deltaList = IntStream.range(0, numbersList.size())
                .mapToObj(i -> numbersList.get(i) - prevData.get(i))
                .collect(Collectors.toCollection(() -> new ArrayList<>(20)));
        prevData = numbersList;
        return deltaList;
    }
    
    private List<Integer> getUsageData ()
    {
        try
        {
            String output = getOutputAndExtractFirstLine();
            output = removeExtraSpace(output);
            output = removeAnyText(output);
            List<String> outputList = splitOutputBySpace(output);
            return convertListItemsToIntegers(outputList);
        }
        catch (IOException e)
        {
            logger.warning(e.getMessage());
            return Collections.emptyList();
        }
    }
    
    private List<Integer> convertListItemsToIntegers (@NotNull Collection<String> outputList)
    {
        return outputList.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
    
    private List<String> splitOutputBySpace (@NotNull String output)
    {
        return List.of(output.split(" "));
    }
    
    @NotNull
    private String removeAnyText (@NotNull String output)
    {
        String cpu = "cpu ";
        return output.substring(output.indexOf(cpu) + cpu.length());
    }
    
    private String removeExtraSpace (CharSequence output)
    {
        return EXTRA_SPACE.matcher(output).replaceAll(" ");
    }
    
    @NotNull
    private String getOutputAndExtractFirstLine () throws IOException
    {
        String output = getCommandOutput(cpuUsageCommand);
        output = output.substring(0, output.indexOf(System.lineSeparator()));
        return output;
    }
    
    @Override
    public String toString ()
    {
        return "LinuxCpuMonitor{" + "cpuUsageCommand=" + cpuUsageCommand + ", prevData=" +
                prevData + '}';
    }
}
