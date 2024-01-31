package lv.lu.df.combopt.rest;

import ai.timefold.solver.core.api.score.analysis.ScoreAnalysis;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.constraint.Indictment;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.SolverManager;
import jakarta.annotation.PostConstruct;
import lv.lu.df.combopt.domain.BinPackingSolution;
import lv.lu.df.combopt.domain.BinPackingSolutionJsonIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
public class PackingController {
    @Autowired
    private SolverManager<BinPackingSolution, String> solverManager;
    @Autowired
    private SolutionManager<BinPackingSolution, HardSoftScore> solutionManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(PackingController.class);
    // atrastos risinājumus glabā vienkāršā HashMap
    private Map<String, BinPackingSolution> solutionMap = new HashMap<>();
    private static final BinPackingSolutionJsonIO solutionIOJSON = new BinPackingSolutionJsonIO();
    @PostMapping("/solve")
    public void solve(@RequestBody BinPackingSolution problem) {
        // palaiž risinājumu ar solverManager. solve metode vienkārši ilgi risinās.
        // Bet solveAndListen visu laiku atgriezīs labāko risinājumu
        solverManager.solveAndListen(
                problem.getSolutionId(), id -> problem, solution -> {
                    //katru risinājumu ievieto HashMap
                    solutionMap.put(solution.getSolutionId(), solution);
                });
    }
    // metode, kura dabū risinājumu pēc tā id
    @GetMapping("/solution")
    public BinPackingSolution solution(@RequestParam String id) {
        return solutionMap.getOrDefault(id, null);
    }
    @GetMapping("/list")
    public List<BinPackingSolution> list() {
        return solutionMap.values().stream().toList();
    }
    @PostMapping("/stop")
    public void stop(@RequestParam String id) {
        solverManager.terminateEarly(id);
    }
    @GetMapping("/score")
    public ScoreAnalysis<HardSoftScore> score(@RequestParam String id) {
        return solutionManager.analyze(solutionMap.get(id));
    }

    @GetMapping("/indictments")
    public Map<Object,Indictment<HardSoftScore>> indictments(@RequestParam String id) {
        return solutionManager.explain(solutionMap.getOrDefault(id, null)).getIndictmentMap();/*.entrySet().stream()
                .map(entry -> {
                    Indictment<HardSoftScore> indictment = entry.getValue();
                    return
                            new SimpleIndictmentObject(entry.getKey(), // indicted Object
                                    indictment.getScore(),
                                    indictment.getConstraintMatchCount(),
                                    indictment.getConstraintMatchSet());
                }).collect(Collectors.toList());*/
    }

}
