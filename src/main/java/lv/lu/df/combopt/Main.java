package lv.lu.df.combopt;


import ai.timefold.solver.core.api.score.ScoreExplanation;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.config.solver.EnvironmentMode;
import ai.timefold.solver.core.config.solver.SolverConfig;
import ai.timefold.solver.core.config.solver.termination.TerminationConfig;
import lv.lu.df.combopt.domain.BinPackingSolution;
import lv.lu.df.combopt.domain.Pack;
import lv.lu.df.combopt.domain.Vehicle;
import lv.lu.df.combopt.solver.ScoreCalculator;
import lv.lu.df.combopt.solver.StreamCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Hello world from Logger");
        LOGGER.debug("Hello world from Logger debugger");

        // Vienkārši statiskie piemēra dati. Nodefinēta, kā public static metode.
        BinPackingSolution problem = BinPackingSolution.generateData(50);

        problem.print();

        // SOLVERA KONFIGURĒŠANA: (alternatīva ir xml failā SolverConfig.xml, mapē resources)
        // Pasakām, kas ir problēma,
        // kas ir maināms (Entity)
        // kā rēķināt izmaksas
        // kā beigt darbu (pēc 10 sekundēm)
        // Pārbaudām, lai risinājums būtu atkārtojams ar FULL_ASSERT režīmu
        SolverFactory<BinPackingSolution> solverFactory = SolverFactory.create((new SolverConfig()
                .withSolutionClass(BinPackingSolution.class)
                .withEntityClasses(Pack.class)
                //.withEasyScoreCalculatorClass(ScoreCalculator.class)
                .withConstraintProviderClass(StreamCalculator.class)
                .withTerminationConfig( new TerminationConfig().withUnimprovedSecondsSpentLimit(10L)))
                .withEnvironmentMode(EnvironmentMode.FULL_ASSERT));

        SolverFactory<BinPackingSolution> solverFactoryFromXML = SolverFactory
                .createFromXmlResource("SolverConfig.xml");

        // kur apkārtnes funkcija? Solveris lieto noklusēto apkārtnes funkciju - swapMoveSelector un changeMoveSelector
        //

        //Izveido jaunu solveri no augstāk rakstītās konfigurācijas
        Solver<BinPackingSolution> solver = solverFactoryFromXML.buildSolver();

        //padod problēmu solverim
        BinPackingSolution solution = solver.solve(problem);

        // Default. Local Search solis darbojas pēc Late Acceptance algoritma.
        SolutionManager<BinPackingSolution, HardSoftScore> solutionManager = SolutionManager.create(solverFactory);
        ScoreExplanation<BinPackingSolution, HardSoftScore> scoreExplanation = solutionManager.explain(solution);
        LOGGER.info(scoreExplanation.getSummary());

        solution.print();
    }

}