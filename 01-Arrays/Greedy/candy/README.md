# WIN #19 — Candy

## Problem

There are `n` children standing in a line.

Each child has a rating represented by the integer array `ratings`.

I need to distribute candies according to two rules:

```text
1. Every child must receive at least 1 candy.

2. If a child has a higher rating than a neighboring child,
   that child must receive more candies.
```

The goal is to return the **minimum total number of candies** required.

For example:

```text
Input:
ratings = [1,0,2]

Output:
5
```

The candies can be distributed as:

```text
Ratings:  1  0  2
Candies:  2  1  2
```

Total:

```text
2 + 1 + 2 = 5
```

Another example:

```text
Input:
ratings = [1,2,2]

Output:
4
```

A valid distribution is:

```text
Ratings:  1  2  2
Candies:  1  2  1
```

Total:

```text
1 + 2 + 1 = 4
```

The important part is that the distribution must satisfy the rating relationships while using the **minimum possible number of candies**.

---

## My Java Solution

```java
class Solution {
    public int candy(int[] ratings) {

        int[] candies = new int[ratings.length];
        Arrays.fill(candies, 1);

        for(int i = 1; i <= ratings.length-1; i++){
            if(ratings[i] > ratings[i-1]){
                candies[i] = candies[i-1]+1;
            }
        }

        for(int i = ratings.length-2; i >= 0;i--){
            if(ratings[i] > ratings[i+1]){
               candies[i] = Math.max(candies[i], candies[i+1]+1);
            }
        }

        int sum = 0;

        for(int number: candies){
            sum += number;
        }
        
        return sum;
    }
}
```

---

## My Thought Process

The first thing I noticed is that every child must receive at least one candy.

So I can start with:

```text
Everyone gets 1 candy
```

For example:

```text
Ratings:
[1,0,2]

Candies:
[1,1,1]
```

This already satisfies the first rule:

```text
Every child has at least 1 candy
```

Now I need to adjust the candy distribution based on the ratings.

The tricky part is that every child can have two neighbors:

```text
Left neighbor
Right neighbor
```

A child can have a higher rating than the child on the left, the child on the right, or both.

So I realized that I can handle these two directions separately.

```text
First pass:
Left → Right

Second pass:
Right → Left
```

The overall idea is:

```text
Initialize everyone with 1 candy
            ↓
First pass from left to right
            ↓
Handle increasing ratings from the left
            ↓
Second pass from right to left
            ↓
Handle increasing ratings from the right
            ↓
Sum all candies
```

---

## Step 1: Give Everyone One Candy

I create an array to store the candy count:

```java
int[] candies = new int[ratings.length];
```

Then I initialize every position to `1`:

```java
Arrays.fill(candies, 1);
```

For example:

```text
ratings = [1,0,2,3,2]

candies = [1,1,1,1,1]
```

This gives every child the minimum possible number of candies.

---

## Step 2: First Pass — Left to Right

I traverse the ratings from left to right:

```java
for(int i = 1; i <= ratings.length-1; i++)
```

For each child, I compare their rating with the child immediately to their left:

```java
if(ratings[i] > ratings[i-1])
```

If the current child has a higher rating, they need more candies than the child on their left.

So I give them one more candy:

```java
candies[i] = candies[i-1] + 1;
```

For example:

```text
ratings = [1,2,3]
```

Initially:

```text
candies = [1,1,1]
```

At index `1`:

```text
ratings[1] > ratings[0]

2 > 1
```

Therefore:

```text
candies[1] = candies[0] + 1
           = 2
```

Now:

```text
candies = [1,2,1]
```

At index `2`:

```text
ratings[2] > ratings[1]

3 > 2
```

Therefore:

```text
candies[2] = candies[1] + 1
           = 3
```

Now:

```text
candies = [1,2,3]
```

The first pass handles increasing sequences from left to right.

---

## What the First Pass Handles

The first pass essentially asks:

```text
Does this child have a higher rating than the child on my left?
```

If yes:

```text
Give the current child more candies than the left neighbor.
```

For example:

```text
Ratings:
1 → 2 → 3 → 4

Candies:
1 → 2 → 3 → 4
```

This works perfectly for an increasing sequence.

However, the first pass cannot handle everything.

Consider:

```text
ratings = [1,2,3,2,1]
```

After the first pass:

```text
Ratings:  1  2  3  2  1
Candies:  1  2  3  1  1
```

The left-side conditions are satisfied.

But the child with rating `3` must also have more candies than the children to the right.

So I need another pass.

---

## Step 3: Second Pass — Right to Left

The second pass goes from right to left:

```java
for(int i = ratings.length-2; i >= 0; i--)
```

Now I compare the current child with the child immediately to the right:

```java
if(ratings[i] > ratings[i+1])
```

If the current child has a higher rating than the child on the right, the current child needs more candies.

Therefore:

```text
candies[i] >= candies[i+1] + 1
```

I implement this using:

```java
candies[i] = Math.max(candies[i], candies[i+1] + 1);
```

---

## Why `Math.max()` Is Important

The first pass may have already given the current child a certain number of candies.

The second pass should not reduce that number.

Consider:

```text
ratings = [1,2,3,2,1]
```

After the first pass:

```text
candies = [1,2,3,1,1]
```

Now the second pass starts from the right.

For the child with rating `2` near the end:

```text
2 > 1
```

So this child needs:

```text
1 + 1 = 2 candies
```

The array becomes:

```text
candies = [1,2,3,2,1]
```

Now consider the child with rating `3`:

```text
3 > 2
```

The right neighbor has `2` candies.

So the current child needs:

```text
2 + 1 = 3
```

But the first pass has already given this child `3`.

Therefore:

```java
Math.max(3, 3)
```

keeps:

```text
3
```

This is important because the child might have a requirement coming from **both directions**.

---

## Handling a Peak

Consider:

```text
ratings = [1,2,3,2,1]
```

The middle child has the highest rating:

```text
        3
       / \
      2   2
     /     \
    1       1
```

The middle child needs more candies than both neighbors.

The first pass gives:

```text
[1,2,3,1,1]
```

The second pass changes the right side:

```text
[1,2,3,2,1]
```

The middle child remains at `3`.

So the final distribution satisfies both directions:

```text
Ratings:  1  2  3  2  1
Candies:  1  2  3  2  1
```

---

## Complete Example Walkthrough

Consider:

```text
ratings = [1,0,2]
```

### Initial State

Every child gets one candy:

```text
Ratings:
[1,0,2]

Candies:
[1,1,1]
```

---

### First Pass

Compare index `1` with index `0`:

```text
0 > 1
```

False.

No change:

```text
[1,1,1]
```

Now compare index `2` with index `1`:

```text
2 > 0
```

True.

So:

```text
candies[2] = candies[1] + 1
```

The array becomes:

```text
[1,1,2]
```

---

### Second Pass

Now move from right to left.

Compare index `1` with index `2`:

```text
0 > 2
```

False.

No change.

Now compare index `0` with index `1`:

```text
1 > 0
```

True.

Therefore:

```text
candies[0] = candies[1] + 1
```

So:

```text
candies[0] = 2
```

The final distribution is:

```text
Ratings:  1  0  2
Candies:  2  1  2
```

Total:

```text
2 + 1 + 2 = 5
```

Therefore:

```text
Output = 5
```

---

## Another Example

Consider:

```text
ratings = [1,2,2]
```

Initial:

```text
candies = [1,1,1]
```

First pass:

```text
2 > 1
```

So:

```text
candies = [1,2,1]
```

For the next child:

```text
2 > 2
```

is false.

So the array remains:

```text
[1,2,1]
```

Second pass:

```text
2 > 2
```

False.

Then:

```text
1 > 2
```

False.

So the final distribution is:

```text
Ratings:  1  2  2
Candies:  1  2  1
```

Total:

```text
1 + 2 + 1 = 4
```

Therefore:

```text
Output = 4
```

This also shows that **equal ratings do not require more candies**.

The rule only applies when:

```text
ratings[i] > ratings[neighbor]
```

---

## Why Two Passes Work

There are two separate directional requirements.

### Left Neighbor

If:

```text
ratings[i] > ratings[i-1]
```

then:

```text
candies[i] > candies[i-1]
```

The left-to-right pass handles this.

### Right Neighbor

If:

```text
ratings[i] > ratings[i+1]
```

then:

```text
candies[i] > candies[i+1]
```

The right-to-left pass handles this.

Therefore:

```text
Left → Right
      ↓
Handle left-side requirements

Right → Left
      ↓
Handle right-side requirements
```

Using `Math.max()` combines the requirements from both directions without losing the larger value.

---

## Pattern Recognition

### Pattern: Greedy + Two-Pass

This problem is a classic **Greedy** problem combined with a **Two-Pass Array Traversal**.

The main idea is to start with the minimum possible value:

```text
1 candy for everyone
```

Then only increase the candy count when a rating relationship requires it.

The first pass handles:

```text
ratings[i] > ratings[i-1]
```

and the second pass handles:

```text
ratings[i] > ratings[i+1]
```

The overall pattern is:

```text
Initialize minimum values
        ↓
Process from left to right
        ↓
Satisfy left-side relationships
        ↓
Process from right to left
        ↓
Satisfy right-side relationships
        ↓
Keep the maximum requirement
        ↓
Calculate the total
```

This pattern is useful when the value assigned to an element depends on its neighbors on **both sides**.

---

## Why Greedy Works

The problem asks for the **minimum** number of candies.

So I don't want to give children more candies than necessary.

I start with:

```text
1 candy
```

for every child.

Then I increase the candy count only when required.

For example:

```text
ratings = [1,2,3]
```

The minimum valid distribution is:

```text
[1,2,3]
```

There is no reason to give:

```text
[5,6,7]
```

The greedy idea is:

```text
Start with the minimum
        ↓
Increase only when a constraint requires it
        ↓
Keep the smallest valid distribution
```

---

## Why I Need the `candies` Array

I use:

```java
int[] candies = new int[ratings.length];
```

because the result for each child needs to be preserved between the two passes.

For example:

```text
ratings = [1,2,3,2,1]
```

After the first pass:

```text
candies = [1,2,3,1,1]
```

The second pass needs these existing values.

This is why I use:

```java
Math.max(...)
```

to combine the requirements from both directions.

Without storing the intermediate candy counts, I would lose the information calculated during the first pass.

---

## Complexity

### Time Complexity

```text
O(n)
```

The solution performs a constant number of linear traversals:

```text
1. Fill the candies array
2. Traverse from left to right
3. Traverse from right to left
4. Calculate the sum
```

Each operation is `O(n)`.

Therefore:

```text
O(n) + O(n) + O(n) + O(n)
```

simplifies to:

```text
O(n)
```

So the overall time complexity is:

```text
O(n)
```

---

### Space Complexity

```text
O(n)
```

I create an additional array:

```java
int[] candies = new int[ratings.length];
```

This array stores the candy count for every child.

Therefore, the auxiliary space is:

```text
O(n)
```

Apart from this array, only a constant number of variables are used.

---

## Key Learning

I learned how a problem with constraints coming from **both sides** can be solved by processing the array in two directions.

Instead of trying to calculate everything at once, I can split the problem into:

```text
Left-side requirement
        +
Right-side requirement
```

The first pass handles:

```text
ratings[i] > ratings[i-1]
```

and the second pass handles:

```text
ratings[i] > ratings[i+1]
```

The most important line in the solution is:

```java
candies[i] = Math.max(candies[i], candies[i+1] + 1);
```

because the current child may already have received more candies from the left-side requirement.

The complete strategy is:

```text
Give everyone 1 candy
        ↓
Left → Right
        ↓
Fix increasing ratings from the left
        ↓
Right → Left
        ↓
Fix increasing ratings from the right
        ↓
Keep the larger requirement
        ↓
Sum the candies
```

The biggest takeaway for me is recognizing:

```text
Neighbor-dependent constraints
        +
Minimum possible values
        +
Requirements from both directions
        ↓
Greedy + Two Passes
```

This problem also taught me that a greedy solution can sometimes require processing the same data from multiple directions. The important part is that each pass has a specific responsibility.

My solution achieves:

```text
O(n) Time
O(n) Auxiliary Space
```

while guaranteeing that every child satisfies the rating constraints with the minimum total number of candies.
