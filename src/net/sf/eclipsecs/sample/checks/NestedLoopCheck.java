package net.sf.eclipsecs.sample.checks;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NestedLoopCheck extends Check {

    private int maxDepth = 3;
	private int countedDepth;


	public void setMax(int limit) {
        maxDepth = limit;
    }

	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.LITERAL_FOR};
	}

	@Override
	public void beginTree(DetailAST root)
	{
		countedDepth = 0;
	}

	@Override
	public void visitToken(DetailAST ast){

        if (ast.getType() == TokenTypes.LITERAL_FOR
        		&& ast.getNumberOfChildren() != 0) {
			increaseNestLevel(ast, "Nest level is too high, decrease to: " + maxDepth);
		}
	}

	@Override
	public void leaveToken(DetailAST ast)
	{
		if(ast.getType() == TokenTypes.LITERAL_FOR) {
			decreaseNestLevel();
		}
	}

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
