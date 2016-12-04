# Gerrymander

How I learned to Google better before trying to solve a problem.

---

FiveThirtyEight's Oliver Roeder writes a column called [The Riddler](http://fivethirtyeight.com/tag/the-riddler/), where he posts a puzzle every week. The [puzzles posted on Oct. 28, 2016](http://fivethirtyeight.com/features/rig-the-election-with-math/) involved gerrymandering a grid of Red and Blue party voters into districts such that the party with the least overall voters could still end up winning a majority of districts.

The smaller of the two puzzles was relatively easy to solve by hand. I thought it would be straightforward to write a program to do the same thing, and in doing so solve the larger puzzle as well.

**Riddler Express**: find a way to divide these voters into five districts such that the Blue Party wins more districts than the Red Party.

![alt text](https://github.com/marshallaf/gerrymander/data/express.png "Riddler Express")

**Riddler Classic**: what are the most districts that the Blue Party could win with an optimal gerrymandering strategy in their favor? What about the Red Party?

![alt text](https://github.com/marshallaf/gerrymander/data/classic.png "Riddler Classic")

---

I sketched a rough idea of what the algorithm would do using a 3x3 board: it would start at the upper-left square and find the possible permutations of the districts. It could determine whether a particular game state could be part of an optimal solution based on how many of the squares in the districts were of the gerrymandering party. For example, in the sketch, the first two solutions on the bottom are excluded because the x party is wasting a vote in a district they will not win - that vote would be better spent in a district with one other x voter, like in the third solution on the bottom.

![alt text](https://github.com/marshallaf/gerrymander/data/sketch.jpg "Sketch of simple problem")

###### In hindsight, I should have recognized when I drew this that this problem would be too complex to brute force.

I initially intended to use a priority queue to sort the non-excluded game states, but couldn't determine a satisfactory priority metric for the boards. Instead I wrote a simple 2d array representation of the board and an exhaustive brute force search, and let it run on the Riddler Express problem. It solved it quickly, finding three different optimal solutions.

However, when I tried it on the Riddler Classic problem, it ran indefinitely. A println statement uncovered why: as the algorithm assigned more and more voters, the number of possible permutations grew exponentially. I attempted several small optimizations to coax small gains out of it, hoping that in sum they could push the time for the Classic puzzle down to something reasonable. I excluded boards that left too-small islands of unassigned voters. I retooled my 2d array representation to a graph representation and implemented depth-first search to find districts. None of it was enough - the problem was too complex.

---

Reading through posts on Stack Overflow, I finally realized the Google query that somehow I hadn't yet searched: "graph partition". The [Wikipedia article](https://en.wikipedia.org/wiki/Graph_partition) told me that the problem, known specifically as balanced partitioning, was NP-complete, thus my naive solution stood no chance at ever solving a large problem.

There are practical solutions to the k-balanced partitioning problem that use approximation techniques. One that interests me is described in ["Partitioning Graphs into Balanced Components." Krauthgamer, *et al.* 2009.](http://www.wisdom.weizmann.ac.il/~robi/papers/KNS-k-partition-SODA09.pdf), but I'll have to revisit it when/if I can understand the math involved.