package goalp.exputil;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 
 * @author grodrigues
 *
 */
public class RandomPrismRepositoryUtil {

	
	public static String concat(Deque<String> deque){
		StringBuilder sb = new StringBuilder();
		for(String str: deque){
			sb.append(str);
		}
		return sb.toString();
	}
	
	/**
	 * Get a k-combination of elements in Deque 'contexts'
	 * @param variables
	 * @return
	 */
	public static Deque<Deque<String>> getCombinations (Deque<String> elements, int k){
		if(k > elements.size()){
			throw new IllegalStateException("Can't make a k-combination of elements, if |elements| < k");
		}
		Deque<Deque<String>> combination = new ArrayDeque<Deque<String>>();
		 if(k == 0){
			 return combination;
		} else if(elements.size() == k){
			//base of induction. k-combination of k-size deque is the deque
			combination.add(elements);
		}else{
		
			//remove the first
			String first = elements.pop();
			//sub combinations. with and without the fist element
			Deque<Deque<String>> subCombinationA = getCombinations(elements, k - 1 );
			Deque<Deque<String>> subCombinationB = new ArrayDeque<Deque<String>>(subCombinationA); 
			
			//with first
			subCombinationA.forEach(element ->{
				element.push(first);;
			});
			
			// the result is the union
			combination.addAll(subCombinationA);
			combination.addAll(subCombinationB);
		}
		return combination;		
	}
}
