package com.upseil.benchmark.sortingsystems;

import org.junit.Test;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.IntSet.IntSetIterator;

public class ArtemisSortingSystemBenchmark {
    
    private static final BenchmarkConfiguration[] configurations = {
                                     //Entity Count, Layer Count, Iterations, Deletions per Iteration, Addings per Iteration
            new BenchmarkConfiguration(     50     ,      5     ,    1000   ,           10           ,          10          ),
            new BenchmarkConfiguration(     50     ,     10     ,    1000   ,           10           ,          10          ),
            new BenchmarkConfiguration(    100     ,     10     ,    1000   ,           10           ,          10          ),
            new BenchmarkConfiguration(    100     ,     10     ,    1000   ,           25           ,          25          ),
            new BenchmarkConfiguration(    100     ,     15     ,    1000   ,           25           ,          25          ),
            new BenchmarkConfiguration(   1000     ,     15     ,    1000   ,           50           ,          50          ),
            new BenchmarkConfiguration(   1000     ,     15     ,    1000   ,          100           ,         100          ),
            new BenchmarkConfiguration(  10000     ,     20     ,    1000   ,          500           ,         500          ),
            new BenchmarkConfiguration( 100000     ,     20     ,    1000   ,         1000           ,        1000          ),
            new BenchmarkConfiguration(1000000     ,     20     ,    1000   ,         2000           ,        2000          )
    };
    
    @Test
    public void benchmark() {
        for (BenchmarkConfiguration configuration : configurations) {
            System.out.println("Running Configuration: " + configuration);
            
            World arraySortingWorld = createArraySortingWorld();
            Benchmark arraySortingBenchmark = new Benchmark("Array Sorting", configuration);
            run(arraySortingWorld, arraySortingBenchmark);
            System.out.println(arraySortingBenchmark);
            
            World mapSortingWorld = createMapSortingWorld();
            Benchmark mapSortingBenchmark = new Benchmark("Map Sorting", configuration);
            run(mapSortingWorld, mapSortingBenchmark);
            System.out.println(mapSortingBenchmark);
            
            System.out.println();
        }
    }

    private World createArraySortingWorld() {
        return new World(new WorldConfigurationBuilder().with(new ArraySortingSystem()).build());
    }

    private World createMapSortingWorld() {
        return new World(new WorldConfigurationBuilder().with(new MapSortingSystem()).build());
    }
     
    private void run(World world, Benchmark benchmark) {
        BenchmarkConfiguration configuration = benchmark.getConfiguration();
        
        fillWorld(world, configuration);
        EntitySubscription entities = world.getAspectSubscriptionManager().get(Aspect.all());

        long start = System.nanoTime();
        world.process();
        long duration = System.nanoTime() - start;
        benchmark.setIntialProcessingDuration(duration);
        
        double completeDuration = 0;
        for (int i = 0; i < configuration.getIterations(); i++) {
            prepareIteration(world, entities, configuration);
            start = System.nanoTime();
            world.process();
            duration = System.nanoTime() - start;
            completeDuration += duration;
        }
        benchmark.setAverageProcessingDuration(completeDuration / configuration.getIterations());
    }

    private void prepareIteration(World world, EntitySubscription subscription, BenchmarkConfiguration configuration) {
        ComponentMapper<Layer> layerMapper = world.getMapper(Layer.class);
        for (int i = 0; i < configuration.getAddingsPerIteration(); i++) {
            int create = world.create();
            layerMapper.create(create).set(MathUtils.random(1, configuration.getLayerCount()));
        }
        
        IntBag entities = subscription.getEntities();
        int[] ids = entities.getData();
        IntSet entitiesToRemove = new IntSet(configuration.getDeletionsPerIteration());
        while (entitiesToRemove.size < configuration.getDeletionsPerIteration()) {
            entitiesToRemove.add(ids[MathUtils.random(0, entities.size() - 1)]);
        }
        IntSetIterator iterator = entitiesToRemove.iterator();
        while (iterator.hasNext) {
            world.delete(iterator.next());
        }
    }

    private void fillWorld(World world, BenchmarkConfiguration configuration) {
        ComponentMapper<Layer> layerMapper = world.getMapper(Layer.class);
        for (int i = 0; i < configuration.getEntityCount(); i++) {
            int create = world.create();
            layerMapper.create(create).set(MathUtils.random(1, configuration.getLayerCount()));
        }
    }
    
}
