import pickledb
# Make sure to change the import statement to .Cache instead of Cache before submitting!
from .Cache import Cache

class KVCachingStore:
    def __init__(self, filename='kv_store.db', cache_size=4, cache_policy='fifo'):
        self.db = pickledb.load(filename, False)
        self.cache = Cache(size=cache_size, policy=cache_policy)

    def get(self, key):
        """
        Get the element with the key from KV store.
        If the element is already in the cache, the element should be gotten from the cache.
        parameter:
            key: the key of the element should get
        return: the value belongs to the key or False if the key does not exist
        """
        ##############################################################
        # YOUR CODE HERE:
        # TODO 1.1: Check if the key is in the cache, if in cache, return the value from cache
        if self.cache.get(key) is not None:
            self.cache.lru_counter = self.cache.lru_counter - 1
            self.cache.counter[key] =  self.cache.counter[key] - 1
            return self.cache.get(key)



        # TODO 1.2: If the value is not in cache, fetch from the database
        else:
            if self.db.get(key) is not None:
                self.cache.put(key,self.db.get(key))

        # TODO 1.3: Update the cache with the fetched value if the value is in database


        # TODO 1.4: Return the value from database, you need to modify the following return value

        return self.db.get(key)
        ##############################################################

    def put(self, key, value):
        """
        Insert the element [key, value] in the KV store
        parameter:
            key: the key of the element to be inserted
            value: the value of the element to be inserted
        return: none
        """
        # Update the database
        self.db.set(key, value)

        # Update the cache
        self.cache.put(key, value)

    def delete(self, key):
        """
        Delete the element whose key = key
        parameter:
            key: The key of element to be deleted
        return: none
        """
        # Delete from the database
        if self.db.exists(key):
            self.db.rem(key)

        # Remove from the cache if present
        if key in self.cache.cacheStore:
            del self.cache.cacheStore[key]

    def close(self):
        """
        Close the database
        parameter: none
        return: none
        """
        # Save the database to disk
        self.db.dump()

    def print_cache_state(self):
        """
        Print the state of the cache
        parameter: none
        return: none
        """
        print("Cache State:")
        for key, value in self.cache.cacheStore.items():
            print(f"Key: {key}, Value: {value}, Counter: {self.cache.counter[key]}")
        print("")
