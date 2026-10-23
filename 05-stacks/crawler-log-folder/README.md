# WIN #104 — 1598. Crawler Log Folder

**LeetCode:** [1598. Crawler Log Folder](https://leetcode.com/problems/crawler-log-folder/)  
**Difficulty:** Easy  
**Topics:** Stack, Array, String, Simulation

---

## 1. Problem

The file system starts in the main folder. We are given an array of strings `logs`, where each string represents a folder navigation operation.

There are three possible operations:

- `"../"` — Move to the parent folder. If already in the main folder, remain there.
- `"./"` — Stay in the current folder.
- `"x/"` — Move into a child folder named `x`.

Return the minimum number of operations required to return to the main folder after processing all logs.

### Example

**Input:**
```text
logs = ["d1/","d2/","../","d21/","./"]
```

**Output:**
```text
2
```

**Explanation:**

1. `"d1/"` — Enter folder `d1`.
2. `"d2/"` — Enter folder `d2` inside `d1`.
3. `"../"` — Move back to `d1`.
4. `"d21/"` — Enter folder `d21` inside `d1`.
5. `"./"` — Stay in `d21`.

We are two folders below the main folder: `d1/d21`.

Therefore, two `"../"` operations are needed to return to the main folder.

---

## 2. Core Idea

Use a **stack to track the current folder depth**.

Every time we enter a child folder, push its name onto the stack. When we move to the parent folder, pop one folder from the stack. If the stack is empty, we are already in the main folder, so moving to the parent does nothing.

The `"./"` operation does not change our location, so we ignore it.

After processing all logs, the stack size represents the number of folders between the current location and the main folder. That is exactly the number of operations required to return to the main folder.

---

## 3. Java Solution

```java
class Solution {
    public int minOperations(String[] logs) {

        Stack<String> stack = new Stack<>();

        for(String log: logs){
            if(stack.isEmpty() && log.equals("../")){
                continue;
            }else if(log.equals("./")){
                continue;
            }else if(!stack.isEmpty() && log.equals( "../")){
                stack.pop();
            }else{
                stack.push(log);
            }
        }
        return stack.size();
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Create a stack

```java
Stack<String> stack = new Stack<>();
```

The stack represents the folders we have entered from the main folder.

For example, if the stack contains:

```text
[d1, d21]
```

we are currently inside `d21`, which is inside `d1`.

The stack size is `2`, so two parent operations are needed to return to the main folder.

### Step 2: Process each log

```java
for(String log: logs)
```

We examine every folder operation in the order it appears.

### Step 3: Handle `"../"` at the main folder

```java
if(stack.isEmpty() && log.equals("../")){
    continue;
}
```

If the stack is empty, we are already in the main folder.

Moving to the parent cannot take us any higher, so we ignore the operation.

### Step 4: Handle `"./"`

```java
else if(log.equals("./")){
    continue;
}
```

The `"./"` operation means staying in the current folder.

Since our folder depth does not change, we skip it.

### Step 5: Move to the parent folder

```java
else if(!stack.isEmpty() && log.equals("../")){
    stack.pop();
}
```

If the stack is not empty and the operation is `"../"`, we remove the current folder from the stack.

For example:

```text
Before: [d1, d2]
Operation: "../"
After:  [d1]
```

We moved from `d1/d2` to `d1`.

### Step 6: Enter a child folder

```java
else{
    stack.push(log);
}
```

If the operation is neither `"../"` nor `"./"`, it represents entering a child folder.

We push the log onto the stack.

For example:

```text
Before: [d1]
Operation: "d2/"
After:  [d1, d2]
```

### Step 7: Return the stack size

```java
return stack.size();
```

Each remaining stack entry represents one level below the main folder.

Therefore, the stack size is the minimum number of parent operations needed to return to the main folder.

---

## 5. Dry Run

**Input:**

```text
logs = ["d1/","d2/","../","d21/","./"]
```

| Operation | Action | Stack | Depth |
|---|---|---|---:|
| `"d1/"` | Enter `d1` | `[d1]` | 1 |
| `"d2/"` | Enter `d2` | `[d1, d2]` | 2 |
| `"../"` | Pop current folder | `[d1]` | 1 |
| `"d21/"` | Enter `d21` | `[d1, d21]` | 2 |
| `"./"` | Stay in current folder | `[d1, d21]` | 2 |

Final stack size:

```text
2
```

**Output:** `2`

We need two parent operations to return to the main folder.

### Another example: Never go below the main folder

**Input:**

```text
logs = ["d1/","../","../","../"]
```

| Operation | Action | Stack | Depth |
|---|---|---|---:|
| `"d1/"` | Enter `d1` | `[d1]` | 1 |
| `"../"` | Return to main folder | `[]` | 0 |
| `"../"` | Already at main folder | `[]` | 0 |
| `"../"` | Already at main folder | `[]` | 0 |

Final stack size is `0`.

**Output:** `0`

This demonstrates why the solution must never allow the folder depth to become negative.

---

## 6. Edge Cases

| Case | Expected behavior |
|---|---|
| Enter a folder and return immediately | Stack becomes empty |
| `"../"` while at the main folder | Ignore the operation |
| `"./"` at any depth | Keep the stack unchanged |
| Enter several nested folders | Stack size increases |
| Mix entering folders and parent operations | Update the stack after each operation |

---

## 7. Complexity Analysis

Let `n` be the number of operations in `logs`.

- **Time Complexity:** `O(n)` — Each log is processed once, with constant-time stack operations.
- **Space Complexity:** `O(n)` — In the worst case, all operations enter new folders and the stack stores up to `n` entries.

The solution is efficient for the given constraints.

---

## 8. Pattern Recognition

This problem demonstrates **stack-based simulation**.

Recognize this pattern when:

- Operations increase or decrease a nested level.
- You need to track the current nesting or navigation state.
- An operation reverses the most recent unresolved step.
- You need to prevent moving above a root or base state.

The same problem can also be solved using a single integer to track folder depth. A stack is useful here for practicing how push and pop operations model navigation.

---

## 10. Final Mental Model

Think of the stack as a record of the folders you are currently inside.

- Enter a child folder: **push**.
- Move to the parent: **pop**, if possible.
- Stay in the current folder: **do nothing**.
- Reach the main folder: **the stack is empty**.

At the end, the number of folders remaining in the stack is the number of parent operations required to return to the main folder.

**Key takeaway:** When a problem involves moving deeper into a hierarchy and undoing the most recent level, a stack can model that movement naturally.