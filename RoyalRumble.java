import java.util.List;
import java.util.ArrayList;

public class RoyalRumble {
  public List<String> getSortedList(List<String> names) {
				System.out.println("starting getSortedList");

  			//Sort by first name
				java.util.Collections.sort(names);
				//[Louis IX, Louis VIII, David II] => [David II, Louis IX, Louis VIII]

//				Loop through each name
						for (int i=0; i< names.size(); i++)
						{
								//if this is the last item ( no next ) break the loop
								if (i == names.size() - 1)
								{
									break;
								}
//							For each item, compare with next (split into firstname/ordinal)
								var thisFirstName = names.get(i).split(" ")[0];
								var thisOrdinal = names.get(i).split(" ")[1];

								var nextFirstName = names.get(i+1).split(" ")[0];
								var nextOrdinal = names.get(i+1).split(" ")[1];

//							if firstname is same, compare ordinals
								if (thisFirstName.equals(nextFirstName))
								{
										//compare value of ordinals (eg: VII: 7)
										int thisOrdinalValue = GetValue(thisOrdinal);
										int nextOrdinalValue = GetValue(nextOrdinal);
										System.out.println("thisOrdinalValue: " + thisOrdinalValue);
										System.out.println("nextOrdinalValue: " + nextOrdinalValue);

										//list is arranged small to large (Louis I before Louis II etc)
										//so swap positions if thisOrdinalValue is larger than nextOrdinalValue
										if(thisOrdinalValue > nextOrdinalValue)
										{
											System.out.println("names before swap: " + names);
											var temp = names.get(i);
											var next = names.get(i+1);
											names.set(i, next);
											names.set(i+1, temp);
											System.out.println("names after swap: " + names);
										}
								}
						}
			//return sorted list
			System.out.println("Final output:" + names + ", returning");
      return names;
  }

  public static int GetValue(String input)
	{
		System.out.println("---------------GetValue---------------");
		System.out.println("input: " + input);
		//convert numerals into modifiable arrays
		List<String> inputArray = new ArrayList<String>();
		char[] tempArray = input.toCharArray();
		for (char c : tempArray)
		{
			inputArray.add(String.valueOf(c));
		}
		System.out.println("inputArray: " + inputArray);

//  		rules only apply to 1-50, only integers, possibilities: I, V, X, L
//			if I, or I after V, I: +1
//				else if I before V, or I before X, I: -1
//			if X before L, X: -10
//			counter = {
//									L: 0, 								(+50)
//									positiveX:0, 					(+10)
//									negativeX:0,					(-10)
//									V:0,									(+5)
//									positiveI:0,					(+1)
//									negativeI:0						(-1)
//								}

			//using vars instead of class because we are only submitting one file
			int L = 0;
			int positiveX = 0;
			int negativeX = 0;
			int V = 0;
			int positiveI = 0;
			int negativeI = 0;

			int indexL = inputArray.indexOf("L");
			//if there is L
			if (indexL != -1)
			{
				L += 1;

//			identify if there are any X before L.
//			if true, count the X and delete both X and L
				if(indexL == 1)
				{
					negativeX += 1;
					inputArray.remove(indexL);
					inputArray.remove(indexL-1);
//			if false, count the L and delete it
				} else
				{
					inputArray.remove(indexL);
				}
			}

			System.out.println("input after checking L " + inputArray);

			int indexV = inputArray.indexOf("V");
			//Given that Is attribute to V over X (eg. XIV = 14, not 16)
			//check for V
			//if there is V, count the V
			if (indexV != -1)
			{
				V += 1;
				//if there are characters before V
				if(indexV > 0)
				{
					//if there is an I before V, minus, and remove V and I
					if (inputArray.get(indexV-1).equals("I"))
					{
						negativeI += 1;
						inputArray.remove(indexV);
						inputArray.remove(indexV-1);
					}

				//if there is only V, remove V
				} else
				{
					inputArray.remove(indexV);
				}
			}

			System.out.println("input after checking V " + inputArray);

			//check for IX
			int findX = inputArray.lastIndexOf("X");
			//if there is at least one X
			if (findX != -1)
			{
				positiveX += 1;
				//check if there is I before this X
				//if true, minus, and remove both X and I
				if (inputArray.get(findX-1).equals("I"))
				{
					negativeI += 1;
					inputArray.remove(findX);
					inputArray.remove(findX-1);
				//if false, remove the X
				} else
				{
					inputArray.remove(findX);
				}
			}
			System.out.println("input after checking IX " + inputArray);

			//check for remaining Is
			int findFirstI = inputArray.indexOf("I");
			if (findFirstI != -1)
			{
				int findLastI = inputArray.lastIndexOf("I");
				for (int i = findLastI; i >= findFirstI; i--)
				{
					positiveI += 1;
					inputArray.remove(i);
				}
			}

		System.out.println("input after checking I " + inputArray);


			//check for remaining Xs
			int remainingXLastIndex = inputArray.lastIndexOf("X");
			//if there are still Xs, add them up
			if (remainingXLastIndex != -1)
			{
				positiveX += (remainingXLastIndex + 1);
			}
			System.out.println("input after checking X: " + inputArray);

		System.out.println("L: " + L);
		System.out.println("positiveX: " + positiveX);
		System.out.println("negativeX: " + negativeX);
		System.out.println("V: " + V);
		System.out.println("positiveI: " + positiveI);
		System.out.println("negativeI: " + negativeI);


		int total = (L * 50) + (positiveX * 10) - (negativeX * 10) + (V * 5) + (positiveI) - (negativeI);
		System.out.println("Total: " + total);
		System.out.println("---------------EndGetValue---------------");
		return total;
	}
}
