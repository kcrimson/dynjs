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
package org.dynjs.jsr223;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringReader;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.dynjs.runtime.DynThreadContext;
import org.junit.Before;
import org.junit.Test;

public class DynJSEngineTest {

	private ScriptEngineManager scriptEngineManager;
	private ScriptEngine scriptEngine;

	@Before
	public void setUp() {
		scriptEngineManager = new ScriptEngineManager();
		scriptEngine = scriptEngineManager.getEngineByName("dynjs");
	}

	@Test
	public void should_eval_script() throws Exception {
		Object result = scriptEngine.eval("var a = 1;");
		assertEquals(DynThreadContext.UNDEFINED, result);
	}

	@Test
	public void should_eval_reader() throws Exception {
		StringReader reader = new StringReader("var a = 1;");
		Object result = scriptEngine.eval(reader);
		assertEquals(DynThreadContext.UNDEFINED, result);
	}

	@Test
	public void should_create_not_null_bindings() {
		Bindings bindings = scriptEngine.createBindings();
		assertNotNull(bindings);
	}

	@Test
	public void should_delegate_property_define_to_bindings_for_script()
			throws Exception {
		Bindings bindings = scriptEngine.createBindings();
		bindings.put("a", "a");
		scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

		scriptEngine.eval("var b=a;");

		assertEquals("a", bindings.get("b"));
	}

	@Test
	public void should_delegate_property_define_to_bindings_for_reader()
			throws Exception {
		Bindings bindings = scriptEngine.createBindings();
		bindings.put("a", "a");
		scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

		StringReader reader = new StringReader("var b=a;");

		scriptEngine.eval(reader);

		assertEquals("a", bindings.get("b"));
	}
}