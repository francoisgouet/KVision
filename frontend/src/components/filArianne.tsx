"use client";

import Link from 'next/link';
import { useRouter } from 'next/navigation';

function FilArianne({ path }) {

  const router = useRouter();

  return (
    <>
        <Link href={"./"}>revenir au domain parent</Link>
    </>
  );
}
export default FilArianne;