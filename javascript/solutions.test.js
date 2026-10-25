const test = require("node:test");
const assert = require("node:assert/strict");
const {
  firstNonRepeatingChar,
  findTwoSum,
  countLeadsByStatus,
} = require("./solutions");

test("firstNonRepeatingChar: examples from the assessment", () => {
  assert.equal(firstNonRepeatingChar("aabbcdd"), "c");
  assert.equal(firstNonRepeatingChar("swiss"), "w");
  assert.equal(firstNonRepeatingChar("aabb"), null);
  assert.equal(firstNonRepeatingChar(""), null);
});

test("firstNonRepeatingChar: case-insensitive count, original case returned", () => {
  assert.equal(firstNonRepeatingChar("sTreSS"), "T");
  assert.equal(firstNonRepeatingChar("AaBbC"), "C");
  assert.equal(firstNonRepeatingChar("aA"), null);
});

test("firstNonRepeatingChar: spaces are ignored, punctuation is counted", () => {
  assert.equal(firstNonRepeatingChar("aa bb"), null);
  assert.equal(firstNonRepeatingChar("   "), null);
  assert.equal(firstNonRepeatingChar("a a b"), "b");
  assert.equal(firstNonRepeatingChar("aa!bb"), "!");
});

test("firstNonRepeatingChar: single character and invalid input", () => {
  assert.equal(firstNonRepeatingChar("z"), "z");
  assert.equal(firstNonRepeatingChar(null), null);
  assert.equal(firstNonRepeatingChar(123), null);
});

test("findTwoSum: examples from the assessment", () => {
  assert.deepEqual(findTwoSum([2, 7, 11, 15], 9), [0, 1]);
  assert.deepEqual(findTwoSum([3, 2, 4], 6), [1, 2]);
  assert.deepEqual(findTwoSum([3, 3], 6), [0, 1]);
  assert.equal(findTwoSum([1, 2, 3], 10), null);
});

test("findTwoSum: does not use the same element twice", () => {
  assert.equal(findTwoSum([3], 6), null);
  assert.equal(findTwoSum([5, 1], 10), null);
});

test("findTwoSum: negative numbers and zero", () => {
  assert.deepEqual(findTwoSum([-3, 4, 3, 90], 0), [0, 2]);
  assert.deepEqual(findTwoSum([0, 4, 0], 0), [0, 2]);
  assert.deepEqual(findTwoSum([-1, -2, -3], -5), [1, 2]);
});

test("findTwoSum: empty array and invalid input", () => {
  assert.equal(findTwoSum([], 5), null);
  assert.equal(findTwoSum(null, 5), null);
  assert.equal(findTwoSum([1, 2], "3"), null);
});

test("countLeadsByStatus: example from the assessment", () => {
  const leads = [
    { name: "Alice", status: "NEW" },
    { name: "Bob", status: "CONTACTED" },
    { name: "Charlie", status: "NEW" },
    { name: "David", status: "QUALIFIED" },
  ];
  assert.deepEqual(countLeadsByStatus(leads), {
    NEW: 2,
    CONTACTED: 1,
    QUALIFIED: 1,
  });
});

test("countLeadsByStatus: empty array returns empty object", () => {
  assert.deepEqual(countLeadsByStatus([]), {});
});

test("countLeadsByStatus: a new status value is counted too", () => {
  const leads = [
    { name: "Eve", status: "LOST" },
    { name: "Frank", status: "LOST" },
  ];
  assert.deepEqual(countLeadsByStatus(leads), { LOST: 2 });
});

test("countLeadsByStatus: does not change the original array", () => {
  const leads = [
    { name: "Alice", status: "NEW" },
    { name: "Bob", status: "NEW" },
  ];
  const copy = JSON.parse(JSON.stringify(leads));
  countLeadsByStatus(leads);
  assert.deepEqual(leads, copy);
});

test("countLeadsByStatus: leads without a valid status are skipped", () => {
  const leads = [
    { name: "Alice", status: "NEW" },
    { name: "NoStatus" },
    { name: "Empty", status: "" },
    null,
  ];
  assert.deepEqual(countLeadsByStatus(leads), { NEW: 1 });
});

test("countLeadsByStatus: known statuses with zero leads show 0", () => {
  const leads = [{ name: "Alice", status: "NEW" }];
  assert.deepEqual(
    countLeadsByStatus(leads, ["NEW", "CONTACTED", "QUALIFIED"]),
    { NEW: 1, CONTACTED: 0, QUALIFIED: 0 }
  );
});
