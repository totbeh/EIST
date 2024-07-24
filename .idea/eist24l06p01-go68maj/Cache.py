from collections import OrderedDict
from collections import Counter
from operator import itemgetter


class Cache:
    def __init__(self, size, policy='fifo'):
        self.size = size  # Cache size
        self.policy = policy  # Policies described in string, could be "fifo","lru","lfu","mru","mfu"
        self.cacheStore = OrderedDict()  # Stores [key,value] pairs for elements in Cache
        self.counter = Counter()  # Counters for every element in Cache
        self.lru_counter = 1  # You may use this for mru and lru policies later

    def _evict(self):
        """
        Use case distinction to evict the element from the cache according to different policies
        parameter: none
        return: none
        """
        ##############################################################
        # YOUR CODE HERE:
        # TODO: Evict element from cache according to different policies, you may use case distinction (if)
        if self.policy == 'fifo':
            self.cacheStore.popitem(False)
        elif self.policy == 'mru':
            manga = self.counter.most_common(1)[0][0]
            self.cacheStore.pop(manga)
        elif self.policy == 'lru':
            min_key, min_count = min(self.counter.items(), key=itemgetter(1))
            self.cacheStore.pop(min_key)
            self.counter.pop(min_key)
        elif self.policy == 'lfu':
            min_key, min_count = min(self.counter.items(), key=itemgetter(1))
            self.cacheStore.pop(min_key)
            self.counter.pop(min_key)
        else:
            manga = self.counter.most_common(1)[0][0]
            self.cacheStore.pop(manga)
            self.counter.pop(manga)


    # HINT: You may find following functions useful: popitem(), min(), max(), lambda, del
        ##############################################################

    def modify_counter(self, key):
        """
        A helper function of get and put which modify the counter according to different policies.
        parameter:
            key: the key of the element, whose counter should be modified
        return: None
        """
        ##############################################################
        # YOUR CODE HERE:
        # TODO: Modify the counter of the key according to different policies.
        #  The policies are given in the problem statement.
        # Hint: Increase the counter only by one after each modification. You may use case distinction (if).

        ##############################################################

    def get(self, key):
        """
        Get the element with the key from cache, modify the counter according to different policies.
        parameter:
            key: the key of the element should get
        return: the value belongs to the key
        """

        ##############################################################
        # YOUR CODE HERE:
        # TODO: Implement the case if key is in the cache, modify the counter according to different policies
        # Hint: You have already implemented the modify_counter method.
        if key in self.cacheStore:
            if self.policy == 'mru' or self.policy == 'lru':
                self.counter[key] = self.lru_counter
                self.lru_counter = self.lru_counter + 1
            elif self.policy == 'lfu' or self.policy == 'mfu':
                if key not in self.counter:
                    self.counter[key] = 1
                self.counter[key] = self.counter.get(key) + 1

            return self.cacheStore.get(key)

        else:
            return None

        # TODO: If the key is not in the Cache, return None

        ##############################################################

    def put(self, key, value):
        """
        Insert the element [key, value] in the cache
        parameter:
            key: the key of the element to be inserted
            value: the value of the element to be inserted
        return: none
        """
        ##############################################################
        # YOUR CODE HERE:
        # TODO: Evict element if the cache is already full and new element is not in the cache
        if key not in self.cacheStore:
            if self.cacheStore.items().__len__() >= self.size:
                self._evict()
        if self.policy == 'mru' or self.policy == 'lru':
            self.counter[key] = self.lru_counter
            self.lru_counter = self.lru_counter + 1
        if self.policy == 'lfu' or self.policy == 'mfu':
            if key not in self.counter:
                self.counter[key] = 0
            self.counter[key] = self.counter.get(key) + 1
        self.cacheStore[key] = value

        # TODO: Add the key and value to the cache

        # TODO: Modify the counter according to the policy
        # Hint: You have already implemented the modify_counter method.

        ##############################################################
