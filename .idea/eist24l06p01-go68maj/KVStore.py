from KVCachingStore import KVCachingStore

##############################################################
# Example usage: you can test your implementation here
# You can modify the cache_policy to "fifo","lru","mru","lfu" or "mfu" and the cache_size.
kv_store = KVCachingStore(cache_size=4, cache_policy='mfu')

# Here are some examples of how to get the value of a key
# and how to put a key-value pair in the cache in the following code.
# The key should be a string.

kv_store.put("A", "1")
kv_store.put("B", "2")
kv_store.put("C", "3")
kv_store.put("D", "4")
kv_store.print_cache_state()
kv_store.put("E", "1")
kv_store.print_cache_state()
kv_store.get("D")
kv_store.print_cache_state()
kv_store.put("F", "77")
kv_store.print_cache_state()
kv_store.get("B")
kv_store.print_cache_state()
kv_store.get("B")
kv_store.print_cache_state()
kv_store.put("aa", 99)
kv_store.print_cache_state()
kv_store.put("w", 99)
kv_store.print_cache_state()
kv_store.put("r", 99)
kv_store.print_cache_state()
kv_store.put("t", 99)
kv_store.print_cache_state()

##############################################################

# Close the KV store to save changes to disk
kv_store.close()
