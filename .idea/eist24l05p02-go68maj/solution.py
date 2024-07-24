# solution.py
import json
import random
import string
import os


def random_key_value(key=None, l=8):
    """
    Generate a random key-value pair.

    Args:
        key (str): Key to use in the tuple.
        l (int): Length of value to generate.

    Returns:
        tuple: A tuple containing a random key and value.
    """
    if key is None:
        key = ''.join(random.choices(string.ascii_lowercase, k=5))
    value = ''.join(random.choices(string.digits, k=l))
    return key, value


def generate_random_operations(n):
    """
    Generate a list of 'N' random operations.

    Args:
        n (int): Number of random operations to generate.

    Returns:
        list: List of randomly generated operations.
    """
    operations = []
    for _ in range(n):
        key, value = random_key_value()
        action = random.choice(["set", "delete", "add", "subtract", "multiply", "divide"])
        if action == "delete":
            operations.append({"action": "delete", "key": key})
        else:
            operations.append({"action": action, "key": key, "value": value})
    return operations


def log_and_apply_operations(operation_list, store, log_file):
    """
    Log the operation and apply it to the store.

    Args:
        operation_list (list of dict): List of operations to log and apply.
        store (dict): The key-value store to apply the operation to.
        log_file (str): The file to log the operation.

    Returns:
        None
    """
    os.makedirs(os.path.dirname(log_file), exist_ok=True)
    with open(log_file, 'a') as file:
        file.write(json.dumps(operation_list) + "\n")

    for operation in operation_list:
        apply_operation(operation, store)


def apply_operation(operation, store):
    """
    Applies one operation to the store.

    Args:
        operation (dict): The operation to apply to the store.
        store (dict): The key-value store to apply the operation to.

    Returns:
        None
    """
    action = operation["action"]
    key = operation["key"]
    if key not in store:
        store[key] = "0"
    ## TODO

    # operation handling here
    if action == "set":
        store[key] = str(operation["value"])
    elif action == "delete":
        store.pop(key)
    elif action == "add":
        store[key] = str(convert_string_to_number(operation["value"]) + convert_string_to_number(store[key]))
    elif action == "subtract":
        store[key] = str(convert_string_to_number(store[key]) - (convert_string_to_number(operation["value"])))
    elif action == "multiply":
        store[key] = str(convert_string_to_number(operation["value"]) * convert_string_to_number(store[key]))
    elif action == "divide":
        if convert_string_to_number(operation["value"]) != 0:
            store[key] = str(convert_string_to_number(store[key]) / convert_string_to_number(operation["value"]))
        # other actions here
    ## TODO


def apply_log(file_name, store):
    """
    Apply the operations from a log file to the store.

    Args:
        file_name (str): The name of the log file.
        store (dict): The key-value store to apply the operations to.

    Returns:
        None
    """
    with open(file_name, 'r') as file:
        for line in file:
            operations = json.loads(line)
            for op in operations:
                apply_operation(op, store)


def convert_string_to_number(num):
    """
    Converts the string representation of a number back to a float.

    Args:
        num (str): The string to convert

    Returns:
        float: the converted number
    """
    return float(num)


def main(initial_kv_store, operation_list_list, undo_operation_list_list, redo_log_file, undo_log_file):
    """
    Perform the main process of logging, applying operations, generating undo logs,
    and comparing states.

    Args:
        initial_kv_store (dict): The initial key-value store.
        operation_list_list (list of list of dict): List of list of operations to perform.
        undo_operation_list_list (list of list of dict): List to store undo operations.
        redo_log_file (str): The file to log redo operations.
        undo_log_file (str): The file to log undo operations.

    Returns:
        tuple: A tuple containing the updated key-value store and the comparison store.
    """
    kv_store = initial_kv_store.copy()

    for operation_list in operation_list_list:
        log_and_apply_operations(operation_list, kv_store, redo_log_file)

    # TODO Step 1: Log and Apply operations
    # Hint: you should consider using redo_log_file with logging function.

    comparison_kv_store = kv_store.copy()

    # Step 2: Generate and write Undo Log
    for operation_list in reversed(operation_list_list):  # Reverse the order for undo operations
        undo_operations_list = []
        for operation in reversed(operation_list):
            action = operation["action"]
            key = operation["key"]

            if action == "set":
                if key in initial_kv_store:
                    undo_operations_list.append({"action": "set", "key": key, "value": initial_kv_store[key]})
                else:
                    undo_operations_list.append({"action": "delete", "key": key})
            elif action == "delete":
                if key in kv_store:
                    undo_operations_list.append({"action": "set", "key": key, "value": kv_store[key]})
            elif action == "add":
                undo_operations_list.append({"action": "subtract", "key": key, "value": operation["value"]})
            elif action == "subtract":
                undo_operations_list.append({"action": "add", "key": key, "value": operation["value"]})
            elif action == "multiply":
                undo_operations_list.append({"action": "divide", "key": key, "value": operation["value"]})
            elif action == "divide":
                if convert_string_to_number(operation["value"]) != 0:
                    undo_operations_list.append({"action": "multiply", "key": key, "value": operation["value"]})

        undo_operation_list_list.append(undo_operations_list)

    # Write undo log.
    with open(undo_log_file, "w") as file:
        for operation_list in undo_operation_list_list:
            file.write(json.dumps(operation_list) + "\n")

        apply_log(undo_log_file, kv_store)


    # Step 6: Comparison of initial state and the state after the log files
    return kv_store, comparison_kv_store
