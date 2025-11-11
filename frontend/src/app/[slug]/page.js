import BackButton from '../../components/btnRetour';
import FilArianne from '../../components/filArianne';
import CollapsibleTree from '../../components/D3/collapsibleTree'
export default async function Page({ params }) {
  const { slug } = await params;
  return (
    // hydratation problem si on met  ici</div></div>
    <>
      <h1>Page : {slug}</h1>
      <FilArianne path={slug}></FilArianne>
      <BackButton></BackButton>
    </>
    );
}