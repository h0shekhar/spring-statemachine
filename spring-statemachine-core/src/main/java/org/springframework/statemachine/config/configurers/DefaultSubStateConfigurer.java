/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.statemachine.config.configurers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.builders.StateMachineStateBuilder;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStates;
import org.springframework.statemachine.config.builders.StateMachineStates.StateData;
import org.springframework.statemachine.config.common.annotation.AnnotationConfigurerAdapter;

public class DefaultSubStateConfigurer<S, E>
		extends AnnotationConfigurerAdapter<StateMachineStates<S, E>, StateMachineStateConfigurer<S, E>, StateMachineStateBuilder<S, E>>
		implements SubStateConfigurer<S, E> {

	private S state;
	private final Object id;
	private final Object parent;

	private S initial;
	private Collection<? extends Action<S, E>> entryActions;
	private Collection<? extends Action<S, E>> exitActions;

	private final Collection<StateData<S, E>> states = new ArrayList<StateData<S, E>>();

	public DefaultSubStateConfigurer(S state, Object id) {
		this(state, id, null);
	}

	public DefaultSubStateConfigurer(S state, Object id, Object parent) {
		this.state = state;
		this.id = id;
		this.parent = parent;
	}

	@Override
	public void configure(StateMachineStateBuilder<S, E> builder) throws Exception {
		states.add(new StateData<S, E>(state, null, entryActions, exitActions));
		builder.add(id, parent, states, initial, null);
	}

	@Override
	public SubStateConfigurer<S, E> initial(S initial) {
		this.initial = initial;
		return this;
	}

	@Override
	public SubStateConfigurer<S, E> entry(Collection<? extends Action<S, E>> entryActions) {
		this.entryActions = entryActions;
		return this;
	}

	@Override
	public SubStateConfigurer<S, E> exit(Collection<? extends Action<S, E>> exitActions) {
		this.exitActions = exitActions;
		return this;
	}

}
