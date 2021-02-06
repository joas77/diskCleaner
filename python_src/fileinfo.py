import os
import hashlib

class FileInfo:
    def __init__(self, filepath):
        self._file = filepath

    def get_size(self):
        return os.path.getsize(self._file)

    def get_chunk_hash(self, chunk_size):
        with open(self._file, "rb") as f:
            file_hash = hashlib.md5()
            chunk = f.read(chunk_size)
            file_hash.update(chunk)

        return file_hash.hexdigest()
