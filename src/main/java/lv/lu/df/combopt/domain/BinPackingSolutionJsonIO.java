package lv.lu.df.combopt.domain;

import ai.timefold.solver.jackson.impl.domain.solution.JacksonSolutionFileIO;

public class BinPackingSolutionJsonIO extends JacksonSolutionFileIO<BinPackingSolution> {
    public BinPackingSolutionJsonIO() { super (BinPackingSolution.class); }
}
