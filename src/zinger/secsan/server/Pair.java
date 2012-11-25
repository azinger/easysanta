package zinger.secsan.server;

import com.google.common.base.*;

import java.io.*;

public class Pair<H, T> implements Serializable
{
	public final H head;
	public final T tail;
	
	public Pair(final H head, final T tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public H getHead() { return head; }
	public T getTail() { return tail; }
	
	public static <H, T> Pair<H, T> of(final H head, final T tail)
	{
		return new Pair<H, T>(head, tail);
	}
	
	public int hashCode()
	{
		return com.google.common.base.Objects.hashCode(this.head, this.tail);
	}
	
	public boolean equals(final Object obj)
	{
		if(obj == null || !this.getClass().isAssignableFrom(obj.getClass()))
			return false;
		final Pair<?, ?> other = (Pair<?, ?>)obj;
		return com.google.common.base.Objects.equal(this.head, other.head) && Objects.equal(this.tail, other.tail);
	}
	
	protected Objects.ToStringHelper toStringHelper()
	{
		return com.google.common.base.Objects.toStringHelper(this)
			.add("head", this.head)
			.add("tail", this.tail);
	}
	
	public String toString()
	{
		return this.toStringHelper().toString();
	}
}
