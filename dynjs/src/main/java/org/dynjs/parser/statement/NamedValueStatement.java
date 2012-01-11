/**
 *  Copyright 2011 Douglas Campos
 *  Copyright 2011 dynjs contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dynjs.parser.statement;

import me.qmx.jitescript.CodeBlock;
import org.dynjs.compiler.DynJSCompiler;
import org.dynjs.parser.Statement;

import static me.qmx.jitescript.util.CodegenUtils.sig;

public class NamedValueStatement implements Statement {

    private final Statement propertyName;
    private final Statement expr;

    public NamedValueStatement(Statement propertyName, Statement expr) {
        this.propertyName = propertyName;
        this.expr = expr;
    }

    @Override
    public CodeBlock getCodeBlock() {
        return CodeBlock.newCodeBlock()
                .append(propertyName.getCodeBlock())
                .append(expr.getCodeBlock())
                .invokeinterface(DynJSCompiler.Types.Scope, "define", sig(void.class, String.class, Object.class));
    }
}
