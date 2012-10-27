package zinger.secsan.gae;

import com.google.common.base.*;

import java.io.*;
import java.util.*;

import javax.jdo.*;
import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Pool
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private com.google.appengine.api.datastore.Key key;
}
