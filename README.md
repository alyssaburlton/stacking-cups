## Stacking Cups

Exploring the mathematics of children's stacking cups.

### Notation

In the code, cups can be represented as "u" or "n", followed by a number denoting size (with a larger number representing a larger cup). When written as a stack separated by spaces, reading from left-to-right is the same as working from the ground upwards. Here is a visual example:

```
n3 n2 u1

    |   |
    |___|
  _________
  |       |
  |       |
  |       |
_____________
|           |
|           |
|           |
```

### Definitions

 - A *stack* is defined as any sequence of cups, with no duplicates and using all of the sizes from 1..n for some n. The individual cups can be in any order or orientation.
 - A *tower* is a stack where there is no nesting (i.e. no cups inside one another - the two "nested" stacks for n=2 are `u2 u1` and `n1 n2`)
 - An *ideal tower* is a tower where all the cups are "locked" in place. For example, placing the smallest cup on top of the biggest cup does not achieve this, as it can slide around laterally. This definition needs nailing down properly.

### Early lemmas / questions to answer

 - What happens to the number of towers and/or ideal towers as N increases? Can we find a closed form for these numbers? What happens to the proportion of these configurations as N -> ∞ ?
 - The act of inverting a tower preserves nesting.
 - The act of inverting a tower preserves locking IF there is no nesting. With nesting this is not true, e.g. "u2 n1 n3" is locked, but its inverse "u3 u1 n2" is not (the smallest cup "falls" down into the largest one)
 - The inverse of an ideal tower is an ideal tower. And subset of an ideal tower is an ideal tower.

### Stretch Questions / Out of Scope

 - What if we allow repeated cups, e.g. buying two of the same set? What properties do we encounter then?
 - When N gets large, we will eventually have a cup that is taller than the two smallest cups stacked on top of each other. How does this change things, what new properties/concerns are introduced?