package net.sf.eclipsecs.sample.checks;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks for the depthness of for-loops.
 * Should max be 3 levels deep.
 * @author Diljhot
 *
 */
public class NestedLoopCheck extends Check {

    private int maxDepth = 3;
	private int countedDepth;


	public void setMax(int limit) {
        maxDepth = limit;
    }

	/**
	 * Used to return the tokens that we're interested in.
	 * Must be implemented and is used when visiting tokens
	 * to specify which types we need/are interested in.
	 */
	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.LITERAL_FOR};
	}

	/**
	 * In the MethodLimitCheck class we don't deal with beginTree.
	 * The reason is simply that it is much more trivial. In this case
	 * we need to traverse the tree, increase/decrease nest levels etc
	 * So it's neccessary to call this method so we have some initialization
	 * time before beginning the traversal
	 */
	@Override
	public void beginTree(DetailAST root)
	{
		countedDepth = 0;
	}

	/**
	 * For every FOR_LITERAL we check the depthness by calling 
	 * the increaseNestLevel.
	 */
	@Override
	public void visitToken(DetailAST ast){

        if (ast.getType() == TokenTypes.LITERAL_FOR
        		&& ast.getNumberOfChildren() != 0) {
			increaseNestLevel(ast, "Nest level is too high, decrease to: " + maxDepth);
		}
	}

	/**
	 * For every token we leave we must remember that the.
	 * nest level must also be decreased.
	 */
	@Override
	public void leaveToken(DetailAST ast)
	{
		if(ast.getType() == TokenTypes.LITERAL_FOR) {
			decreaseNestLevel();
		}
	}

	/**
	 * Immeadiately checks if the depth so far > allowed depth.
	 * If this is the case it throws the error/log message.
	 * If not the case we simply increase the counted depth so far.
	 * @param ast 
	 * @param msg
	 */
	public void increaseNestLevel(DetailAST ast, String msg)
	{
		if (countedDepth > maxDepth) {
			log(ast.getLineNo(), msg , countedDepth, maxDepth);
		}
		++countedDepth;
	}

	public void decreaseNestLevel()
	{
		--countedDepth;
	}
}
