[![Build Status](https://travis-ci.com/PolomskiBartlomiej/groovy-dsl.svg?token=PwyvjePQ7aiAX51hSYLE&branch=master)](https://travis-ci.com/PolomskiBartlomiej/groovy-dsl)

# groovy-dsl
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

    on `transitionEvent` move from `fromState` to `toState`
    
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