import os
import subprocess

def git_configured():
    try:
        subprocess.check_output(["git", "config", "--get", "user.email"])
        subprocess.check_output(["git", "config", "--get", "user.name"])
        return True
    except subprocess.CalledProcessError:
        return False

def branch_exists(branch_name):
    try:
        subprocess.check_output(["git", "rev-parse", "--verify", branch_name])
        return True
    except subprocess.CalledProcessError:
        return False

patch_directory = "patches"
patch_files = os.listdir(patch_directory)

if not git_configured():
    print("Git user name and email are not set. Please configure Git using the following commands:")
    print("git config --global user.name \"Your Name\"")
    print("git config --global user.email \"Your Email\"")
    exit(1)

branch_name = "feature/"

if branch_exists(branch_name):
    print(f"Branch {branch_name} already exists. Skipping.")
    print(f"Checking out: {branch_name}")
    os.system(f"git checkout {branch_name}")
else:
    print(f"Checking out: {branch_name}")
    os.system(f"git checkout -b {branch_name}")

def modify_java_file(filename):
    """
    Modifies a Java file by replacing a string with a new string.

    Args:
        filename: Path to the Java file.
    """
    with open(filename, 'r') as f:
        content = f.read()

        # Replace the string
        modified_content = content.replace("Hello EIST!", "Hello EIST 2024!")

    # Write to a temporary file first
    with open(filename + ".tmp", 'w') as f:
        f.write(modified_content)

    # Overwrite the original file (assuming success)
    import os
    os.replace(filename + ".tmp", filename)

print("Modify the src/de/tum/in/ase/eist/Hello.java file.")
modify_java_file("src/de/tum/in/ase/eist/Hello.java")

print("Staging the changes.")
os.system("git add .")
print(f"Committing to: {branch_name}")
os.system(f'git commit -m "Apply changes" --author="SOMEONE ELSE <other.developer@tum.de>"')

os.system("git checkout main")
