package com.upseil.benchmark.sortingsystems;

public class Benchmark {
    
    private static final long nanoToMilli = 1000000;
    
    private final String targetName;
    private final BenchmarkConfiguration configuration;
    
    private double intialProcessingDuration;
    private double averageProcessingDuration;

    public Benchmark(String targetName, BenchmarkConfiguration configuration) {
        this.targetName = targetName;
        this.configuration = configuration;
    }
    
    public String getTargetName() {
        return targetName;
    }

    public BenchmarkConfiguration getConfiguration() {
        return configuration;
    }
    
    public double getIntialProcessingDuration() {
        return intialProcessingDuration;
    }

    public void setIntialProcessingDuration(double intialProcessingDuration) {
        this.intialProcessingDuration = intialProcessingDuration;
    }

    public double getAverageProcessingDuration() {
        return averageProcessingDuration;
    }

    public void setAverageProcessingDuration(double averageProcessingDuration) {
        this.averageProcessingDuration = averageProcessingDuration;
    }
    
    @Override
    public String toString() {
        return targetName + " Benchmark:\n" +
               "  Initial processing duration: " + (intialProcessingDuration / nanoToMilli) + "\n" +
               "  Average processing duration: " + (averageProcessingDuration / nanoToMilli);
    }
    
}
