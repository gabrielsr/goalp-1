package goalp.exputil;

import com.panayotis.gnuplot.dataset.Point;
import com.panayotis.gnuplot.dataset.PointDataSet;

public class DataSetBuilder<N extends Number> {
	
	private PointDataSet<N> dataset;
	
	private DataSetBuilder(){
		this.dataset = new PointDataSet<N>();;
	}
	
	public static DataSetBuilder<Number> create(){
		return new DataSetBuilder<Number>();
	}
	
	@SuppressWarnings("unchecked")
	public DataSetBuilder<N> addPoint(N... coords){
		Point<N> point = new Point<N>(coords);
		dataset.add(point);
		return this;
	}

	public PointDataSet<N>  build(){
		PointDataSet<N> built = this.dataset;
		this.dataset = null;
		return built;
	}
}
