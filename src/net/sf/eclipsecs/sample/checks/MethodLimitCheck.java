
package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * With inspiration from the custom checkstyle extension class.
 * Also the example used on http://checkstyle.sourceforge.net/writingchecks.html
 * @author Diljhot
 *
 */
public class MethodLimitCheck extends Check {

    //The maximum of methods to be allowed in one class or interface
    private int max = 10;

    /**
     * We're interested in Class and Interface tokens.
     * It's the methods within these that we wish to count the methods.
     * @return int[]
     */
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Visits the tokens of type CLASS_DEF and INTERFACE_DEF.
     */
    public void visitToken(DetailAST ast) {

        //It's the OBJ_BLOCK token that contains either interface or class.
        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);

        int methodDefs = objBlock.getChildCount(TokenTypes.METHOD_DEF);
        // report error if limit is exceeded.
        if (methodDefs > max) {
            log(ast.getLineNo(),
                    "Too many methods. Max: " + max
                    + " methods should be defined",
                    max);
        }
    }
}