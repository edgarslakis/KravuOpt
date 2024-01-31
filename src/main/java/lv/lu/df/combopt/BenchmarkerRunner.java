package lv.lu.df.combopt;

import ai.timefold.solver.benchmark.api.PlannerBenchmark;
import ai.timefold.solver.benchmark.api.PlannerBenchmarkFactory;
import lv.lu.df.combopt.domain.BinPackingSolution;
import lv.lu.df.combopt.domain.BinPackingSolutionJsonIO;

import java.io.File;

public class BenchmarkerRunner {
    public static void  main(String[] args) {
        PlannerBenchmarkFactory benchmarkFactory = PlannerBenchmarkFactory
                .createFromSolverConfigXmlResource("SolverConfig.xml");

        PlannerBenchmarkFactory benchmarkFactoryFromXML = PlannerBenchmarkFactory
                .createFromXmlResource("BenchmarkConfig.xml");

        //BinPackingSolution problem = BinPackingSolution.generateData(10);

        BinPackingSolutionJsonIO binPackingSolutionJsonIO = new BinPackingSolutionJsonIO();
        binPackingSolutionJsonIO.write(BinPackingSolution.generateData(10), new File("data/scale10.json"));

        /*PlannerBenchmark benchmark = benchmarkFactoryFromXML.buildPlannerBenchmark(
                BinPackingSolution.generateData(5),
                BinPackingSolution.generateData(10),
                BinPackingSolution.generateData(15),
                BinPackingSolution.generateData(25),
                BinPackingSolution.generateData(50),
                BinPackingSolution.generateData(100)
        );*/
        PlannerBenchmark benchmark = benchmarkFactoryFromXML.buildPlannerBenchmark();
        benchmark.benchmarkAndShowReportInBrowser();

    }
}
