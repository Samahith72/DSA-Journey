# WIN #111 — 71. Simplify Path

**LeetCode:** [71. Simplify Path](https://leetcode.com/problems/simplify-path/)  
**Difficulty:** Medium  
**Topics:** String, Stack

---

## Problem Statement

You are given an absolute path for a Unix-style file system. The path always begins with `/`.

Your task is to simplify the path and return its canonical representation.

### Rules

- A single period `.` represents the current directory and should be ignored.
- A double period `..` represents the parent directory and removes the most recent directory from the path.
- Multiple consecutive slashes, such as `//` or `///`, are treated as a single slash.
- Any other sequence of periods, such as `...` or `....`, is a valid directory name.
- The simplified path must begin with a single slash.
- Directory names must be separated by exactly one slash.
- The path must not end with a slash unless it is the root directory.

### Example 1

**Input:**
```text
path = "/home/"
```

**Output:**
```text
"/home"
```

**Explanation:** The trailing slash is removed.

### Example 2

**Input:**
```text
path = "/home//foo/"
```

**Output:**
```text
"/home/foo"
```

**Explanation:** Consecutive slashes are treated as a single slash.

### Example 3

**Input:**
```text
path = "/home/user/Documents/../Pictures"
```

**Output:**
```text
"/home/user/Pictures"
```

**Explanation:** `..` removes the previous directory, `Documents`.

### Example 4

**Input:**
```text
path = "/../"
```

**Output:**
```text
"/"
```

**Explanation:** You cannot move above the root directory.

### Example 5

**Input:**
```text
path = "/.../a/../b/c/../d/./"
```

**Output:**
```text
"/.../b/d"
```

**Explanation:** `...` is a valid directory name, `..` removes the previous directory, and `.` is ignored.

---

## Core Idea

The problem requires us to process directory names while respecting the special meanings of `.` and `..`.

A **stack** is a natural fit because moving into a directory adds it to the current path, while moving to its parent removes the most recently added directory.

### Main Strategy

1. Split the path using `/` to obtain individual path components.
2. Traverse each component.
3. Ignore empty components and `.`.
4. If a component is `..`, remove the top directory from the stack if one exists.
5. Otherwise, treat the component as a valid directory name and push it onto the stack.
6. Build the simplified path by adding `/` before every directory name.
7. If no directories remain, return `/`.

### Why Use a Stack?

A stack follows the **Last In, First Out (LIFO)** principle.

For example:

```text
/home/user/Documents/..
```

The directory `Documents` is the most recently added directory. Encountering `..` means moving to its parent, so `Documents` must be removed.

The stack handles this operation directly using `pop()`.

---

## Java Solution

```java
class Solution {
    public String simplifyPath(String path) {

        Stack<String> stack = new Stack<>();
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.equals(".") || part.equals("")) {
                continue;
            }

            if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(part);
            }
        }

        StringBuilder sb = new StringBuilder();

        for (String s : stack) {
            sb.append("/").append(s);
        }

        return sb.length() == 0 ? "/" : sb.toString();
    }
}
```

---

## Step-by-Step Explanation

### 1. Create the Stack

```java
Stack<String> stack = new Stack<>();
```

The stack stores valid directory names in the order they appear in the simplified path.

For example, after processing:

```text
/home/user
```

The stack contains:

```text
Bottom -> home
          user <- Top
```

The most recently added directory is at the top.

### 2. Split the Path

```java
String[] parts = path.split("/");
```

The `split("/")` operation divides the path into components using `/` as the separator.

For example:

```text
Input: "/home/user/Documents/../Pictures"
```

The components are conceptually:

```text
["", "home", "user", "Documents", "..", "Pictures"]
```

The initial empty component appears because the path begins with `/`.

Consecutive slashes can also produce empty components. Your solution handles these during traversal.

### 3. Ignore Empty Components and `.`

```java
if (part.equals(".") || part.equals("")) {
    continue;
}
```

There are two conditions to ignore.

**Empty component:** An empty component can result from leading or consecutive slashes. It does not represent a directory.

**Single period `.`:** This represents the current directory, so it does not change the path.

For example:

```text
/home/./user
```

Ignoring `.` leaves:

```text
/home/user
```

The `continue` statement skips the current iteration and moves to the next component.

### 4. Handle `..`

```java
if (part.equals("..")) {
    if (!stack.isEmpty()) {
        stack.pop();
    }
}
```

The double period `..` means moving up one directory.

If the stack is not empty, remove the top directory using `pop()`.

For example:

```text
Stack before: [home, user, Documents]
Component:    ..
Stack after:  [home, user]
```

The directory `Documents` is removed.

The `isEmpty()` check prevents the algorithm from removing a directory when already at the root.

For example:

```text
Input: /../
Output: /
```

Since the stack is empty, `pop()` is not executed.

### 5. Push Valid Directory Names

```java
else {
    stack.push(part);
}
```

Any component that is neither empty, `.` nor `..` is treated as a valid directory name.

For example:

```text
Component: home
Stack:     [home]
```

Importantly, the algorithm does not treat every sequence of periods as a special directory instruction.

For example:

```text
...
....
```

Both are valid directory names because only the exact strings `.` and `..` have special meanings.

### 6. Build the Simplified Path

```java
StringBuilder sb = new StringBuilder();

for (String s : stack) {
    sb.append("/").append(s);
}
```

Once all components have been processed, iterate over the stack and append each directory with a leading slash.

For example, if the stack contains:

```text
[home, user, Pictures]
```

The resulting string is:

```text
/home/user/Pictures
```

Using `StringBuilder` allows the result to be assembled efficiently.

### 7. Handle the Root Directory

```java
return sb.length() == 0 ? "/" : sb.toString();
```

This is a ternary conditional expression.

It is equivalent to:

```java
if (sb.length() == 0) {
    return "/";
} else {
    return sb.toString();
}
```

The condition checks whether the resulting string is empty.

- If the stack contained no directory names, `sb` remains empty, so the method returns `/`.
- Otherwise, it returns the constructed canonical path.

For example:

```text
Input:  /../
Output: /
```

No directory remains after processing the path, so the root directory is returned.

---

## Dry Run

Consider the input:

```text
path = "/home/user/Documents/../Pictures/./"
```

After splitting the path, the components are conceptually:

```text
["", "home", "user", "Documents", "..", "Pictures", ".", ""]
```

The algorithm processes each component as follows.

| Step | Component | Action | Stack after processing |
|---:|---|---|---|
| 1 | `""` | Ignore empty component | `[]` |
| 2 | `home` | Push directory | `[home]` |
| 3 | `user` | Push directory | `[home, user]` |
| 4 | `Documents` | Push directory | `[home, user, Documents]` |
| 5 | `..` | Pop `Documents` | `[home, user]` |
| 6 | `Pictures` | Push directory | `[home, user, Pictures]` |
| 7 | `.` | Ignore current directory | `[home, user, Pictures]` |
| 8 | `""` | Ignore empty component | `[home, user, Pictures]` |

The final stack is:

```text
[home, user, Pictures]
```

Building the path produces:

```text
/home/user/Pictures
```

**Output:**
```text
"/home/user/Pictures"
```

---

## Edge Cases

| Case | Input | Expected output | Explanation |
|---|---|---|---|
| Root directory | `/` | `/` | No directory names exist. |
| Trailing slash | `/home/` | `/home` | The output does not end with a slash. |
| Consecutive slashes | `/home//foo///bar` | `/home/foo/bar` | Empty components are ignored. |
| Current directory | `/home/./user` | `/home/user` | `.` is ignored. |
| Parent directory | `/home/user/..` | `/home` | The previous directory is removed. |
| Move above root | `/../../` | `/` | The stack cannot be popped when empty. |
| Multiple parent operations | `/a/b/../../c` | `/c` | Both `b` and `a` are removed. |
| Valid period sequence | `/.../a/../b` | `/.../b` | `...` is treated as a directory name. |
| Empty result after simplification | `/a/..` | `/` | All directory names are removed. |

---

## Complexity Analysis

Let `n` be the length of the input path.

### Time Complexity: O(n)

Splitting the path requires O(n) time.

The algorithm then processes each component once. Each directory name is pushed at most once and popped at most once.

Building the final path also takes time proportional to the total length of the directory names.

Therefore, the overall time complexity is **O(n)**.

### Space Complexity: O(n)

The split array can contain O(n) components, and the stack can store directory names whose total length is O(n).

The `StringBuilder` also requires space for the resulting path.

Therefore, the overall auxiliary space complexity is **O(n)**.

---

## Pattern Recognition

**Pattern: Stack-Based Path Normalization**

This problem uses a stack to maintain the current sequence of valid directories.

Recognize this approach when:

- An operation adds an element to a sequence.
- Another operation reverses the most recent addition.
- The order of remaining elements must be preserved.
- You need to process navigation or undo operations from left to right.

### Recognition Rule

For Unix-style path simplification:

```text
Empty component -> Ignore
"."             -> Ignore
".."            -> Pop if possible
Other component -> Push
```

After processing all components, join the remaining directory names with a single `/` before each name.

If no directory names remain, return `/`.

---

## Key Takeaways

1. Splitting the path converts it into manageable directory components.
2. Ignore empty components and the exact component `.`.
3. Use `pop()` for `..` to move to the parent directory.
4. Push all other components, including valid names such as `...`.
5. Check `isEmpty()` before popping to prevent moving above the root.
6. Use `StringBuilder` to construct the canonical path.
7. Return `/` when the final path contains no directory names.

**Final Mental Model:** Read the path one component at a time. Ignore the current directory, remove the most recent directory for `..`, and push every valid directory name. Finally, join the remaining names with single slashes to construct the canonical path.