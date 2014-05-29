package net.sf.eclipsecs.sample.checks;

import org.apache.commons.lang.StringUtils;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks if variables are defined using the camelCase convention.
 * @author Diljhot
 *
 */
public class CamelCaseCheck extends Check {

    /**
	 * The pattern to match the variables against.
	 */
    private String camelCasePattern = "[a-z]+[A-Z]+[a-zA-Z]+";

	@Override
	public int[] getDefaultTokens() {

		return new int[] {TokenTypes.VARIABLE_DEF};
	}

	@Override
	public void visitToken(DetailAST ast){

         if(ast.getType() == TokenTypes.VARIABLE_DEF){
        	 //Retrieve the actual identifier in the variable definition
        	 DetailAST identifier = ast.findFirstToken(TokenTypes.IDENT);

        	 //The identifier contains some characters after the name which we don't want
        	 String trimmedIdentifier =
        			 StringUtils.substringBefore(identifier.toString(), "[");

        	 //If the identifier is defined using camelCase then..
        	 if (!trimmedIdentifier.matches(camelCasePattern)){
                log(ast.getLineNo(), ast.getColumnNo(),
                		"This variable is not camelCase. Change it. "
                		, trimmedIdentifier);
        	 }
         }
	}

}
