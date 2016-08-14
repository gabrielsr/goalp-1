package goalp.evaluation.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecSpec implements Cloneable{

	public Map<String, Object> repoSpec = new HashMap<>();
	
	public Map<String, Object> getRepoSpec() {
		return repoSpec;
	}

	public void put(String key, Object value){
		this.repoSpec.put(key, value);
	}
	
	public Integer getInteger(String key){
		return (Integer) this.repoSpec.get(key);
	}
	
	public <T> T getObject(Class<T> t, String key){
		Object obj = this.repoSpec.get(key);
		if(obj != null && t.isAssignableFrom(obj.getClass())){
			return (T) this.repoSpec.get(key);
		}else{
			return null;
		}
	}
	
	
	public ExecSpec clone(){
		ExecSpec clone = new ExecSpec();
		this.repoSpec.forEach((key, value) ->{
			clone.repoSpec.put(key, value);
		});
		return clone;
	}
	
	@Override
	public String toString() {
		return "ExecSpec [repoSpec=" + repoSpec + "]";
	}
}
