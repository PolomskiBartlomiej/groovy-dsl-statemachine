[![Build Status](https://travis-ci.com/PolomskiBartlomiej/groovy-dsl.svg?token=PwyvjePQ7aiAX51hSYLE&branch=master)](https://travis-ci.com/PolomskiBartlomiej/groovy-dsl)

# groovy-dsl-statemachine
In this project we show how to write complex DSL using groovy powerful
features (closures, command chains, `@DelegatesTo`).

# preface
**Domain-Specific Languages** are small languages, focused on a particular 
aspect of a software system. They allow business experts to read or 
write code without having to be programming experts.

# project description
We provide complex and fully tested DSL for programming finite state 
machine:
* `Grammar`: immutable class representing DSL sentence:

    > on `transitionEvent` move from `fromState` to `toState`
    
    which is represented in code as:
    ```
    new Grammar().on(transitionEvent).from(fromState).to(toState)
    ```
    or using closures:
    ```
    Grammar.make {
                    on transitionEvent, { from fromState, { to toState } }
                 }
    ```
    _Remark_: note that the word order in sentence is optional, so:
    ```
    Grammar.make {
                    on transitionEvent, { from fromState, { to toState } }
                 }    
    ```
    is basically equivalent to
    ```
    Grammar.make {
                    from _fromState, { to _toState, { on _event } }
                 }
    ```
    and so on.
    
* `Fsm` - immutable class representing finite state machine 
(transitions container)
    * we create `Fsm` objects using `FsmBuilder`
    * we could load Fsm (`transitions` map) using closure:
        ```
        def fsm2 = Fsm.load {
            apply { on _event1, { from _state1, { to _state2 } } }
            apply { on _event2, { from _state2, { to _state3 } } }
            initialState _state0
        }        
        ```
        will load: `_state0` as initial state and 
        `[_event1:[_state1, _state2], _event2:[_state2, _state3]]`
        as transitions map
    * order of functions in closure is optional
    * to move from current state to the other state we call
        ```
        fsm.fire(event)
        ```
        _Remark_: if we cannot move from current state to the requested
        state on the given event we will stay in the current state - no exception
        is thrown.
    
# tests
**Coverage**: `86%`
