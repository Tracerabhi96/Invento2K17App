package infinityinc.com.invento2k17.Notification;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Abhishek Jakkannavar on 3/31/2017.
 */

public class FirebaseHelper {



    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //WRITE IF NOT NULL
    public Boolean save(Spacecraft spacecraft)
    {
        if(spacecraft==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("Spacecraft").push().setValue(spacecraft);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }
    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        spacecrafts.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Spacecraft spacecraft=ds.getValue(Spacecraft.class);
            spacecrafts.add(spacecraft);
        }
    }
    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<Spacecraft> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return spacecrafts;
    }

}
