import os
import argparse
from fileinfo import FileInfo


if __name__=="__main__":

    parser = argparse.ArgumentParser(description="Script to find duplicates in paths")
    parser.add_argument("path", type=str, help="Path to search for duplicates")

    args = parser.parse_args()

    files_size_hash_map = {}

    for dirpath, dirnames, filenames in os.walk(args.path):
        for filename in filenames:
            abs_path = os.path.join(dirpath, filename)

            # FIXME: this is ugly
            try:
                fileinfo = FileInfo(abs_path)
                fsize_fhash = fileinfo.get_size(), fileinfo.get_chunk_hash(8192)

                if fsize_fhash in files_size_hash_map:
                    files_size_hash_map[fsize_fhash].append(abs_path)
                    # print(f"file of {fsize_fhash}  -- path: {abs_path}")
                else:
                    files_size_hash_map[fsize_fhash] = [abs_path]
            except Exception as e:
                print(e)

    for sizehash in files_size_hash_map:
        if len(files_size_hash_map[sizehash])>=2:
            size, fhash = sizehash
            print(f"\nfiles of size = {size//1024} kB and hash: {fhash}")
            files = files_size_hash_map[sizehash]
            for filename in files:
                print(filename)

