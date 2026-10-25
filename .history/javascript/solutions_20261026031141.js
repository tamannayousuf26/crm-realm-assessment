function firstNonRepeatingChar(str) {
  if (typeof str !== "string") {
    return null;
  }

  const counts = new Map();
  for (const ch of str) {
    if (ch.trim() === "") continue;
    const key = ch.toLowerCase();
    counts.set(key, (counts.get(key) || 0) + 1);
  }

  // loop the original string again so the original case is returned
  for (const ch of str) {
    if (ch.trim() === "") continue;
    if (counts.get(ch.toLowerCase()) === 1) {
      return ch;
    }
  }

  return null;
}

function findTwoSum(nums, target) {
  if (!Array.isArray(nums) || typeof target !== "number") {
    return null;
  }

  const seen = new Map(); // value -> index

  for (let i = 0; i < nums.length; i++) {
    const needed = target - nums[i];
    // check before adding nums[i], so the same element is not used twice
    if (seen.has(needed)) {
      return [seen.get(needed), i];
    }
    if (!seen.has(nums[i])) {
      seen.set(nums[i], i);
    }
  }

  return null;
}

function countLeadsByStatus(leads, allStatuses = []) {
  const result = {};

  // known statuses start at 0 so they show up even with no leads
  for (const status of allStatuses) {
    result[status] = 0;
  }

  if (!Array.isArray(leads)) {
    return result;
  }

  for (const lead of leads) {
    if (!lead || typeof lead.status !== "string" || lead.status === "") {
      continue;
    }
    result[lead.status] = (result[lead.status] || 0) + 1;
  }

  return result;
}

module.exports = { firstNonRepeatingChar, findTwoSum, countLeadsByStatus };
