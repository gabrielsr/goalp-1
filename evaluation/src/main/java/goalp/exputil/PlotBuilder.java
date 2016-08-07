package goalp.exputil;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.dataset.Point;
import com.panayotis.gnuplot.dataset.PointDataSet;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.terminal.PostscriptTerminal;

public class PlotBuilder {
 		
	private JavaPlot p;
	
	private PointDataSet<Number> dataSet;
	private String labelFont = "Arial";
	private int fontSize = 20;
	 
	private PlotBuilder(){
		
		this.p = new JavaPlot();
	}
	
	public static PlotBuilder create(){
		return new PlotBuilder();
	}
	
	
	public PlotBuilder asEps(String fileDest){
        PostscriptTerminal epsf = new PostscriptTerminal(fileDest);
        epsf.setColor(true);

        p.setTerminal(epsf);
        return this;
	}
	
	public PlotBuilder xLabel(String label){
        p.getAxis("x").setLabel(label, labelFont, fontSize);
        return this;
	}

	public PlotBuilder yLabel(String label){
        p.getAxis("y").setLabel(label, labelFont, fontSize);
        return this;
	}

	public PlotBuilder addDataSet(PointDataSet<Number> dataSet) {
		p.addPlot(dataSet);
		return this;
	}
	
	public PlotBuilder addDataSet(double[][] plot) {
		p.addPlot(new DataSetPlot(plot));
		return this;
	}

	public PlotBuilder addIntegerDataSet(PointDataSet<Integer> dataSet) {
		p.addPlot(dataSet);
		return this;		
	}
	
	public PlotBuilder addDoubleDataSet(PointDataSet<Double> dataSet) {
		p.addPlot(dataSet);
		return this;
	}
	
	public PlotBuilder addPoint(Number x, Number y){
		if(dataSet == null){
			dataSet = new PointDataSet<Number>();
		}
		dataSet.add(new Point<Number>(x,y));
		return this;
	}

	public void plot(){

		if(dataSet != null){
			p.addPlot(dataSet);
		}
		p.plot();
	}

}
