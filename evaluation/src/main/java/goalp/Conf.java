package goalp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Conf {

	static Map<Keys, String> confs = new HashMap<>();
	
	public enum Keys {
		
		RESULT_FILE ("restult_"+(new Date()).toLocaleString());

		String _default;

		Keys(String _default){
			this._default = _default;
		}
		
		public String getDefault(){
			return this._default;
		}
	}
	
	public static String get(Keys key){
		String value = confs.get(key);
		if(value == null){
			value = key.getDefault();
		}
		return value;
	}
}
