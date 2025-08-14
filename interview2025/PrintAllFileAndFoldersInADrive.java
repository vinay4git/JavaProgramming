package interview2025;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PrintAllFileAndFoldersInADrive {

    /*
GET https://drive.google.com/{userAuthToken}/{full folder path}
GET https://drive.google.com/jfj8nj3n32823nd912jdn/a/b/c
There is a get API which actually takes a folder path as pathParameter.And returns all its files and folders in that folder.
Write a programme to return all the file folder structure.
- a
-b
-c
- x
- m.doc
- n.jpg
- p.pdf
- y
--- f1
--- f2

root --> "/"


[
    {
        "path": "/a/b/c/x",
        "type": "folder"
    },
    {
        "path": "/a/b/c/m.doc",
        "type": "file"
    },
    {
        "path": "/a/b/c/n.jpg",
        "type": "file"
    },
    {
        "path": "/a/b/c/p.pdf",
        "type": "file"
    },
    {
        "path": "/a/b/c/y",
        "type": "folder"
    }
]
*/
    public static void main(String[] args) {



    }




    public List<Item> getImmediateChildItemRestClient(String path, String userAuthToken) {
        return new ArrayList<>();
        //
    }



    void printPathsOfAllItemsInUsersGoogleDrive(String userAuthToken) {
        Queue<Item> pendingFolders = new LinkedList<>();

        Item root = new Item("/", "folder");
        pendingFolders.add(root);

        while (!pendingFolders.isEmpty()) {
            Item currentFolder = pendingFolders.poll();

            List<Item> itemsInCurrentFolder = getImmediateChildItemRestClient(currentFolder.path, userAuthToken);
            System.out.println("Folder Path : " + currentFolder.path);
            for (Item item : itemsInCurrentFolder) {
                if (item.type.equals("file")) {
                    System.out.println("File Path : " + item.path);
                } else {
                    pendingFolders.add(item);
                }
            }
        }
    }

}


class Item {
    String path;
    String type;

    public Item(String path, String type) {
        this.path = path;
        this.type = type;
    }
}
